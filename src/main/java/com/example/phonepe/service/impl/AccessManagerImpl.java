package com.example.phonepe.service.impl;

import com.example.phonepe.dao.DocumentDao;
import com.example.phonepe.dao.UserDao;
import com.example.phonepe.models.Document;
import com.example.phonepe.models.User;
import com.example.phonepe.service.AccessManager;

import java.util.List;

public class AccessManagerImpl implements AccessManager {
    private final UserDao userDao;
    private final DocumentDao documentDao;
    public AccessManagerImpl(){
        userDao = UserDao.getInstance();
        documentDao = DocumentDao.getInstance();
    }
    @Override
    public void editViewAccessMethod(Integer documentId, String userName, String password, Integer addViewAccessUser) {
        Document document = this.documentDao.getDocument(documentId);
        User author = document.getAuthor();
        if(author.getUserName().equals(userName) && author.getPassword().equals(password)){
            List<Integer> viewAccessList = document.getViewAccessList();
            viewAccessList.add(addViewAccessUser);
            document.setViewAccessList(viewAccessList);
            this.documentDao.updateDocument(document, author.getUserId());
        }
        else{
            System.out.println("Access Denied to Edit View Access");
        }
    }

    @Override
    public void editEditAccessMethod(Integer documentId, String userName, String password, Integer addEditAccessUser) {
        Document document = this.documentDao.getDocument(documentId);
        User author = document.getAuthor();
        if(author.getUserName().equals(userName) && author.getPassword().equals(password)){
            List<Integer> editAccessList = document.getEditAccessList();
            editAccessList.add(addEditAccessUser);
            document.setViewAccessList(editAccessList);
            this.documentDao.updateDocument(document, author.getUserId());
        }
        else{
            System.out.println("Access Denied to Edit Edit Access");
        }
    }
}
