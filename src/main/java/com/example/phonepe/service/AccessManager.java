package com.example.phonepe.service;

public interface AccessManager {
    void editViewAccessMethod(Integer documentId, String userName, String password, Integer addViewAccessUser);
    void editEditAccessMethod(Integer documentId, String userName, String password, Integer addEditAccessUser);
}
