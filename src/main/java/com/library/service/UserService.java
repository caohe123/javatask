package com.library.service;

import com.library.dao.UserDao;
import com.library.model.User;

public class UserService {
    private UserDao userDao = new UserDao();

    public User login(String username, String password) {
        return userDao.login(username, password);
    }
    public boolean register(User user){

        User exist =
                userDao.getUserByUsername(user.getUsername());

        if(exist!=null){

            return false;

        }

        return userDao.addUser(user)>0;
    }
}