package com.example.phonepe.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private Integer userId;
    private String userName;
    private String password;
    private List<Document> authoredDocuments;
    private List<Document> viewAccessDocuments;

    public User(Integer userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        authoredDocuments = new ArrayList<>();
        viewAccessDocuments = new ArrayList<>();
    }
}
