package com.example.phonepe.service;

public interface UserManager {
    void createUser(String userName, String password, Integer userId);
    void loginUser(Integer userId, String password);
}
