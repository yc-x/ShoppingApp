package org.example.shoppingapp.dao;

import org.example.shoppingapp.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends AbstractHibernateDao<User>{

    public UserDao() {
        this.setClassType(User.class);
    }

    public List<User> getAllUsers() {
        return this.getAll();
    }

    public User findUserById(long userId) {
        return this.findById(userId);
    }

    public void addUser(User item) {
        this.add(item);
    }
}
