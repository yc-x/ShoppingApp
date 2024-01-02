package org.example.shoppingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.dao.UserDao;
import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    @Transactional
    public void createUser(User user){
        userDao.addUser(user);
    }

    @Transactional
    public User getUserById(Long userId){
        return userDao.findUserById(userId);
    }

    @Transactional
    public List<Product> getWatchlistProductsByUserId(Long userId){
        return userDao.getWatchlistProductsByUserId(userId);
    }

}
