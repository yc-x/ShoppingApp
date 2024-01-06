package org.example.shoppingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.dao.OrderDao;
import org.example.shoppingapp.dao.OrderItemDao;
import org.example.shoppingapp.dao.ProductDao;
import org.example.shoppingapp.dao.UserDao;
import org.example.shoppingapp.domain.Order;
import org.example.shoppingapp.domain.OrderItem;
import org.example.shoppingapp.domain.OrderItemDetail;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final ProductDao productDao;
    private final UserDao userDao;

    @Transactional
    public List<Order> getAllOrders(){
        return orderDao.getAllOrders();
    }

    @Transactional
    public void createOrder(Order order, List<OrderItem> orderItems){
        orderDao.addOrder(order);
        for(OrderItem orderItem : orderItems){
            orderItemDao.createOrderItem(orderItem);
            productDao.updateProductQuantityById(orderItem.getProduct().getId(),
                    -orderItem.getQuantity());
        }
    }

    @Transactional
    public Order getOrderById(Long orderId){
        return orderDao.getOrderById(orderId);
    }

    @Transactional
    public List<Order> getOrdersByUserId(Long userId){
        return orderDao.getOrdersByUserId(userId);
    }

    @Transactional
    public List<OrderItem> getOrderItemDetailByOrderId(Long orderId){
        return orderItemDao.getOrderItemDetailByOrderId(orderId);
    }

    @Transactional
    public void cancelOrderById(Long orderId){
        Order order = orderDao.getOrderById(orderId);
        Set<OrderItem> orderItemSet = order.getOrderItemSet();
        for(OrderItem orderItem : orderItemSet){
            productDao.updateProductQuantityById(orderItem.getProduct().getId(), orderItem.getQuantity());
            // orderItemDao.deleteOrderItemById(orderItem.getId());
        }
        orderDao.updateOrderStatusById(orderId, "Canceled");
    }
}
