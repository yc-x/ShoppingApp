package org.example.shoppingapp.dao;

import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.domain.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.HashSet;
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

    public User getUserById(Long userId) {
        return this.findById(userId);
    }

    public void addUser(User item) {
        this.add(item);
    }

    public Set<Product> getWatchlistProductsByUserId(Long userId){
        Session currentSession = this.getCurrentSession();
        String queryString = "select p from Product p join p.users u where u.id=:userId";
        List<Product> result = currentSession.createQuery(queryString, Product.class)
                .setParameter("userId", userId)
                .list();
        return new HashSet<>(result);

//        CriteriaBuilder cb = currentSession.getCriteriaBuilder();
//        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
//        Root<Product> productRoot = cq.from(Product.class);
//        // productRoot.join("user_table", JoinType.INNER);


        // why?
        // User user = this.findUserById(userId);
        // System.out.println(getCurrentSession().contains(user));

        // return user.getWatchlistProducts();
    }
}
