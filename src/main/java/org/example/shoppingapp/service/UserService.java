package org.example.shoppingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.dao.UserDao;
import org.example.shoppingapp.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    @Transactional
    public void createUser(User user){
        userDao.addUser(user);
    }

}
