package org.example.shoppingapp.dao;

import org.example.shoppingapp.domain.Order;
import org.example.shoppingapp.domain.Product;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public class OrderDao extends AbstractHibernateDao<Order> {
    public OrderDao() {
        this.setClassType(Order.class);
    }

    public List<Order> getAllOrders() {
        return this.getAll();
    }

    public Order getOrderById(Long orderId) {
        return this.findById(orderId);
    }

    public void addOrder(Order item) {
        this.add(item);
    }

    public List<Order> getOrdersByUserId(Long userId){
        Session currentSession = this.getCurrentSession();
        String queryString = "select o from Order o join o.user u where u.id=:userId";
        return currentSession.createQuery(queryString, Order.class)
                .setParameter("userId", userId)
                .list();
    }

    public void updateOrderStatusById(Long orderId, String newStatus){
        Order order = this.getOrderById(orderId);
        order.setOrderStatus(newStatus);
        getCurrentSession().update(order);
    }
}
