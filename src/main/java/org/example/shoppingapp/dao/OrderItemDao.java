package org.example.shoppingapp.dao;

import org.example.shoppingapp.domain.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDao extends AbstractHibernateDao<OrderItem> {
    public OrderItemDao() {
        this.setClassType(OrderItem.class);
    }

    public List<OrderItem> getAllOrderItems() {
        return this.getAll();
    }

    public OrderItem findOrderItemById(long id) {
        return this.findById(id);
    }

    public void createOrderItem(OrderItem item) {
        super.add(item);
    }
}
