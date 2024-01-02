package org.example.shoppingapp.dao;

import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.domain.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class UserDao extends AbstractHibernateDao<User>{

    public UserDao() {
        this.setClassType(User.class);
    }

    public List<User> getAllUsers() {
        return this.getAll();
    }

    public User findUserById(Long userId) {
        return this.findById(userId);
    }

    public void addUser(User item) {
        this.add(item);
    }

    public List<Product> getWatchlistProductsByUserId(Long userId){
        Session currentSession = this.getCurrentSession();
        String queryString = "select p from Product p join p.users u where u.id=:userId";
        Query query = currentSession.createQuery(queryString);
        query.setParameter("userId", userId);
        return (List<Product>) query.list();
        // why?
        // User user = this.findUserById(userId);
        // System.out.println(getCurrentSession().contains(user));

        // return user.getWatchlistProducts();
    }
}
