package com.example.phonepe.dao;

import com.example.phonepe.exception.AccessDeniedException;
import com.example.phonepe.exception.UserExistsException;
import com.example.phonepe.exception.UserNotFoundException;
import com.example.phonepe.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    public static UserDao userDaoInstance;
    private Map<Integer, User> userMap;

    public UserDao(){
        userMap = new HashMap<>();
    }

    public static UserDao getInstance(){
        if(userDaoInstance==null){
            userDaoInstance = new UserDao();
        }
        return userDaoInstance;
    }

    public boolean addUser(User user) throws UserExistsException {
        if(this.userMap.containsKey(user.getUserId())){
            throw new UserExistsException();
        }
        this.userMap.put(user.getUserId(), user);
        return true;
    }

    public User loginUser(Integer userId, String password) throws UserNotFoundException, AccessDeniedException{
        if(!this.userMap.containsKey(userId)){
            throw new UserNotFoundException();
        }
        User user = userMap.get(userId);
        if(!user.getPassword().equals(password)){
            throw new AccessDeniedException();
        }
        return user;
    }

    public User getUser(Integer userId){
        return this.userMap.get(userId);
    }

    public void updateUser(User user){
        this.userMap.put(user.getUserId(), user);
    }

}
