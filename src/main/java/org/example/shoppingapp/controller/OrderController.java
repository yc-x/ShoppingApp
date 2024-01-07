package org.example.shoppingapp.controller;


import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.domain.*;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.dto.order.OrderRequest;
import org.example.shoppingapp.dto.order.OrderResponse;
import org.example.shoppingapp.dto.order.SingleOrderRequest;
import org.example.shoppingapp.exception.NotEnoughInventoryException;
import org.example.shoppingapp.security.AuthUserDetail;
import org.example.shoppingapp.service.OrderService;
import org.example.shoppingapp.service.ProductService;
import org.example.shoppingapp.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    private Set<String> getAuthUserAuthorities(){
        return  SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    @GetMapping("/all")
    public DataResponse getAllOrders(){
        List<Order> orders;
        if(!getAuthUserAuthorities().contains("Admin")){
            Long currentUserId = Long.valueOf(SecurityContextHolder.getContext()
                    .getAuthentication().getName());
            orders = orderService.getOrdersByUserId(currentUserId);
        }
        else{
            orders = orderService.getAllOrders();
        }

        List<OrderResponse> orderResponses = orders.stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getId())
                        .datePlaced(order.getDatePlaced())
                        .status(order.getOrderStatus())
                        .orderItemDetails(
                            orderService.getOrderItemDetailByOrderId(order.getId())
                                    .stream()
                                    .map(OrderItemDetail::buildOrderItemDetailFromOrderItem)
                                    .collect(Collectors.toList())
                        )
                        .build())
                .collect(Collectors.toList());
        return DataResponse.builder()
                .message("Successfully get all orders")
                .success(true)
                .data(orderResponses)
                .build();
    }

    @GetMapping("/{orderId}")
    public DataResponse getOrderById(@PathVariable Long orderId){
        if(!getAuthUserAuthorities().contains("Admin")){
            Set<Long> orderIdSet = getOrderIdsForCurrentUser();
            if(!orderIdSet.contains(orderId)){
                return DataResponse.builder()
                        .success(false)
                        .message("This order is not placed by you!")
                        .build();
            }
        }
        Order order = orderService.getOrderById(orderId);
        if(order == null){
            return DataResponse.builder()
                    .success(false)
                    .message("Order id doesn't exist!")
                    .build();
        }
        List<OrderItemDetail> orderItemDetails = orderService.getOrderItemDetailByOrderId(orderId)
                .stream().map(OrderItemDetail::buildOrderItemDetailFromOrderItem)
                .collect(Collectors.toList());
        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .datePlaced(order.getDatePlaced())
                .orderItemDetails(orderItemDetails)
                .status(order.getOrderStatus())
                .build();

        return DataResponse.builder()
                .success(true)
                .message("Successfully get an order")
                .data(orderResponse)
                .build();
    }

    @PostMapping("")
    @ResponseBody
    public DataResponse createOrders(@RequestBody @Valid OrderRequest orderRequest,
                                     BindingResult bindingResult)
            throws NotEnoughInventoryException
    {
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            return buildErrorContent(errors);
        }
        String userIdStr = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Long userId = Long.valueOf(userIdStr);
        User currentUser = userService.getUserById(userId);
        List<SingleOrderRequest> orderRequestList = orderRequest.getOrderRequests();
        boolean areItemsEnough = orderRequestList.stream()
                .allMatch(this::isEnough);
        if(!areItemsEnough){
            throw new NotEnoughInventoryException(
                    "One of the items in your order stocking is not enough!"
            );
//            return DataResponse.builder()
//                    .success(false)
//                    .message("One of the items in your order stocking is not enough!")
//                    .build();
        }
        Order newOrder = Order.builder()
                .datePlaced(Timestamp.valueOf(LocalDateTime.now()))
                .user(currentUser)
                .orderStatus("Processing")
                .build();
        List<OrderItem> orderItems = orderRequestList.stream()
                .map(o -> convertToOrderItem(o, newOrder))
                .collect(Collectors.toList());

        orderService.createOrder(newOrder, orderItems);
        return DataResponse.builder()
                .success(true)
                .message("Order created")
                .build();
    }

    @PatchMapping("/{orderId}/cancel")
    public DataResponse cancelOrder(@PathVariable Long orderId){
        Set<Long> orderIdSet = getOrderIdsForCurrentUser();
        if(!orderIdSet.contains(orderId)){
            return DataResponse.builder()
                    .success(false)
                    .message("This order is not placed by you!")
                    .build();
        }
        Order order = orderService.getOrderById(orderId);
        if(!order.getOrderStatus().equals("Processing")){
            return DataResponse.builder()
                    .success(false)
                    .message("This order is not under processing")
                    .build();
        }
        orderService.cancelOrderById(orderId);
        return DataResponse.builder()
                .success(true)
                .message("The order " + orderId + " has been canceled!")
                .build();
    }

    @PatchMapping("/{orderId}/complete")
    @PreAuthorize("hasAuthority('Admin')")
    public DataResponse completeOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if(order.getOrderStatus().equals("Processing")){
            orderService.completeOrderById(orderId);
            return DataResponse.builder()
                    .success(true)
                    .message("The order " + orderId + " has been completed!")
                    .build();
        }
        return DataResponse.builder()
                .success(false)
                .message("This order status cannot be changed to completed!")
                .build();
    }

    private Set<Long> getOrderIdsForCurrentUser(){
        Long currentUserId = Long.valueOf(SecurityContextHolder.getContext()
                .getAuthentication().getName());
        return orderService.getOrdersByUserId(currentUserId)
                .stream().map(Order::getId)
                .collect(Collectors.toSet());
    }

    private DataResponse buildErrorContent(List<FieldError> errors){
        StringBuilder errorMessage = new StringBuilder();
        errors.forEach(error -> errorMessage.append(error.getObjectName())
                .append(": ")
                .append(error.getDefaultMessage())
                .append("\n"));
        return DataResponse.getGeneralInvalidResponse(errorMessage.toString(),
                "Order request not in correct format, please check your request.");
    }

    private boolean isEnough(SingleOrderRequest singleOrderRequest){
        Product product = productService.getProductById(singleOrderRequest.getProductId());
        return singleOrderRequest.getQuantity() <= product.getQuantity();
    }

    private OrderItem convertToOrderItem(SingleOrderRequest singleOrderRequest, Order order){
        Product product = productService.getProductById(singleOrderRequest.getProductId());
        return OrderItem.builder()
                .purchasedPrice(product.getRetailPrice())
                .wholesalePrice(product.getWholesalePrice())
                .quantity(singleOrderRequest.getQuantity())
                .order(order)
                .product(product)
                .build();
    }

}
