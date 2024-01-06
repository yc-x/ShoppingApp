package org.example.shoppingapp.dao;

import org.example.shoppingapp.domain.Order;
import org.example.shoppingapp.domain.OrderItem;
import org.example.shoppingapp.domain.OrderItemDetail;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractHibernateDao<OrderItem> {
    public OrderItemDao() {
        this.setClassType(OrderItem.class);
    }

    public List<OrderItem> getAllOrderItems() {
        return this.getAll();
    }

    public OrderItem getOrderItemById(long id) {
        return this.findById(id);
    }

    public void createOrderItem(OrderItem item) {
        super.add(item);
    }

    public List<OrderItem> getOrderItemDetailByOrderId(Long orderId){
        Session currentSession = this.getCurrentSession();
        String queryString = "select oi from OrderItem oi join oi.order o where o.id =:orderId ";
        return currentSession.createQuery(queryString, OrderItem.class)
                .setParameter("orderId", orderId)
                .list();
    }

    public void deleteOrderItemById(Long orderItemId){
        Session currentSession = this.getCurrentSession();
        OrderItem orderItem = this.getOrderItemById(orderItemId);
        this.getCurrentSession().delete(orderItem);
    }

}
