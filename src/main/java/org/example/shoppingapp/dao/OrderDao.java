package org.example.shoppingapp.dao;

import org.example.shoppingapp.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao extends AbstractHibernateDao<Order> {
    public OrderDao() {
        this.setClassType(Order.class);
    }

    public List<Order> getAllOrders() {
        return this.getAll();
    }

    public Order findOrderById(Long orderId) {
        return this.findById(orderId);
    }

    public void addOrder(Order item) {
        this.add(item);
    }
}
