package org.example.shoppingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.dao.OrderDao;
import org.example.shoppingapp.domain.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;

    @Transactional
    public List<Order> getAllOrders(){
        return orderDao.getAllOrders();
    }
}
