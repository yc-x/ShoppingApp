package org.example.shoppingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.dao.ProductDao;
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
    private final ProductDao productDao;

    @Transactional
    public void createUser(User user){
        userDao.addUser(user);
    }

    @Transactional
    public User getUserById(Long userId){
        return userDao.getUserById(userId);
    }

    @Transactional
    public Set<Product> getWatchlistProductsByUserId(Long userId){
        return userDao.getWatchlistProductsByUserId(userId);
    }

    @Transactional
    public void addProductToWatchlistByProductIdAndUserId(Long productId, Long userId){
        User user = userDao.getUserById(userId);
        Product product = productDao.getProductById(productId);
        user.getWatchlistProducts().add(product);

        product.getUsers().add(user);
    }

    @Transactional
    public void removeProductFromWatchlistByProductIdAndUserId(Long productId, Long userId){
        User user = userDao.getUserById(userId);
        Product product = productDao.getProductById(productId);
        user.getWatchlistProducts().remove(product);
        product.getUsers().remove(user);
    }

}
