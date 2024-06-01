package com.example.phonepe.service.impl;

import com.example.phonepe.dao.DocumentDao;
import com.example.phonepe.dao.UserDao;
import com.example.phonepe.exception.DocumentDoesNotExistException;
import com.example.phonepe.exception.DocumentExistsException;
import com.example.phonepe.models.Document;
import com.example.phonepe.models.User;
import com.example.phonepe.service.DocumentManager;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class DocumentManagerImpl implements DocumentManager {
    private final DocumentDao documentDao;
    private final UserDao userDao;
    private static Integer documentCount;
    public DocumentManagerImpl(){
        this.documentDao = DocumentDao.getInstance();
        this.userDao = UserDao.getInstance();
        documentCount = 0;
    }
    @Override
    public void createDocument(String text, Integer authorId, List<Integer> editAccessList, List<Integer> viewAccessList) {
        documentCount++;
        Document document = new Document();
        document.setText(text);
        User author = this.userDao.getUser(authorId);
        document.setAuthor(author);
        document.setDocumentId(documentCount);
        document.setEditAccessList(editAccessList);
        document.setViewAccessList(viewAccessList);
        try{
            this.documentDao.addDocument(document);
            System.out.println("Document added successfully");
        } catch (DocumentExistsException e) {
            System.out.println("Document already exists with id : "+documentCount);
        }

    }

    @Override
    public void updateDocument(String text, Integer authorId, List<Integer> editAccessList, List<Integer> viewAccessList, Integer documentId) {
        Document document = this.documentDao.getDocument(documentId);
        if(document.getEditAccessList().contains(authorId)){
            document.setText(text);
            if(!editAccessList.isEmpty()){
            document.setEditAccessList(editAccessList);}
            if(!viewAccessList.isEmpty()){
            document.setViewAccessList(viewAccessList);}
            document.setLatestVersionId(document.getLatestVersionId()+1);
            List<Document> versionList = document.getVersions();
            versionList.add(document);
            document.setVersions(versionList);
            this.documentDao.updateDocument(document, authorId);
        }
        else{
            System.out.println("Access Denied to Edit");
        }
    }

    @Override
    public void deleteDocument(String userName, String password, Integer documentId) {
        Document document = this.documentDao.getDocument(documentId);
        User author = document.getAuthor();
        if(author.getUserName().equals(userName) && author.getPassword().equals(password)){
            try {
                this.documentDao.deleteDocument(documentId);
            } catch (DocumentDoesNotExistException e) {
                System.out.println("Document does not exist");
            }
        }
        else{
            System.out.println("Could not find User");
        }
    }

    @Override
    public void getActiveVersion(Integer documentId) {
        try{
            Integer documentVersion = this.documentDao.getLatestActiveVersion(documentId);
            System.out.println("Latest version : "+documentVersion);
        } catch (DocumentDoesNotExistException e) {
            System.out.println("Document does not exist with id : "+documentId);
        }
    }

    @Override
    public void revertToPreviousVersion(Integer documentId, String userName, String password, Integer versionId) {
        Document document = this.documentDao.getDocument(documentId);
        User author = document.getAuthor();
        if(author.getUserName().equals(userName) && author.getPassword().equals(password)){
            try {
                Document newDoc = this.documentDao.revertToPreviousVersion(documentId, versionId);
                System.out.println("Reverted to previous version : {}"+ newDoc.getLatestVersionId());
            } catch (DocumentDoesNotExistException e) {
                System.out.println("Document does not exist with id : "+documentId);
            }
        }
    }
}
