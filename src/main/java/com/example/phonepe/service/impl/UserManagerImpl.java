package com.example.phonepe.service.impl;

import com.example.phonepe.dao.UserDao;
import com.example.phonepe.exception.AccessDeniedException;
import com.example.phonepe.exception.UserExistsException;
import com.example.phonepe.exception.UserNotFoundException;
import com.example.phonepe.models.User;
import com.example.phonepe.service.UserManager;

public class UserManagerImpl implements UserManager {
    private final UserDao userDao;
    private static Integer userCount;

    public UserManagerImpl(){
        userDao = UserDao.getInstance();
        userCount = 0;
    }
    @Override
    public void createUser(String userName, String password, Integer userId) {

        User user = new User(userId, userName, password);
        try{
            this.userDao.addUser(user);
            System.out.println("Successfully created user : " + user);
        }catch (UserExistsException e){
            System.out.println("User already exists");
        }
    }

    @Override
    public void loginUser(Integer userId, String password) {
        try{
            User user = this.userDao.loginUser(userId, password);
            System.out.println("User found : " + user);
        } catch (UserNotFoundException e){
            System.out.println("User Not found");
        } catch (AccessDeniedException e){
            System.out.println("Wrong userId or password");
        }
    }
}
