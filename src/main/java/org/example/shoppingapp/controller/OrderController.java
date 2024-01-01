package org.example.shoppingapp.controller;


import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.domain.Order;
import org.example.shoppingapp.domain.OrderItem;
import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.domain.User;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.dto.order.OrderRequest;
import org.example.shoppingapp.dto.order.SingleOrderRequest;
import org.example.shoppingapp.service.OrderService;
import org.example.shoppingapp.service.ProductService;
import org.example.shoppingapp.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/all")
    public DataResponse getAllOrders(){
        // TODO: build orders.
        return DataResponse.builder()
                .build();
    }

    @PostMapping("")
    @ResponseBody
    public DataResponse createOrders(@RequestBody @Valid OrderRequest orderRequest,
                                     BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            return buildErrorContent(errors);
        }
        User dummyUser = userService.getUserById(2L);
        // TODO: replace this user with current user.
        List<SingleOrderRequest> orderRequestList = orderRequest.getOrderRequests();
        Order newOrder = Order.builder()
                .datePlaced(Timestamp.valueOf(LocalDateTime.now()))
                .user(dummyUser)
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

    private DataResponse buildErrorContent(List<FieldError> errors){
        StringBuilder errorMessage = new StringBuilder();
        errors.forEach(error -> errorMessage.append(error.getObjectName())
                .append(": ")
                .append(error.getDefaultMessage())
                .append("\n"));
        return DataResponse.getGeneralInvalidResponse(errorMessage.toString(),
                "Order request not in correct format, please check your request.");
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
