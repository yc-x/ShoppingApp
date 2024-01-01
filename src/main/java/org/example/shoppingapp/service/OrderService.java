package org.example.shoppingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.dao.OrderDao;
import org.example.shoppingapp.dao.OrderItemDao;
import org.example.shoppingapp.domain.Order;
import org.example.shoppingapp.domain.OrderItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;

    @Transactional
    public List<Order> getAllOrders(){
        return orderDao.getAllOrders();
    }

    @Transactional
    public void createOrder(Order order, List<OrderItem> orderItems){
        orderDao.addOrder(order);
        for(OrderItem orderItem : orderItems){
            orderItemDao.createOrderItem(orderItem);
        }
    }


}
