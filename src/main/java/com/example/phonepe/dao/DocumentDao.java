package com.example.phonepe.dao;

import com.example.phonepe.exception.DocumentDoesNotExistException;
import com.example.phonepe.exception.DocumentExistsException;
import com.example.phonepe.models.Document;
import com.example.phonepe.models.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DocumentDao {
    public static DocumentDao documentDaoInstance;
    private final UserDao userDao;
    private Map<Integer, Document> documentMap;
    private Map<Integer, Document> userDocumentMap; //last updated by whom

    public static DocumentDao getInstance(){
        if(documentDaoInstance==null){
            documentDaoInstance = new DocumentDao();
        }
        return documentDaoInstance;
    }
    public DocumentDao(){
        documentMap = new HashMap<>();
        userDocumentMap = new HashMap<>();
        userDao = UserDao.getInstance();
    }

    public boolean addDocument(Document document) throws DocumentExistsException {
        if(this.documentMap.containsKey(document.getDocumentId())){
            throw new DocumentExistsException();
        }
        this.documentMap.put(document.getDocumentId(), document);
        User author = document.getAuthor();
        List<Document> addedDoc = author.getAuthoredDocuments();
        addedDoc.add(document);
        author.setAuthoredDocuments(addedDoc);
        this.userDao.updateUser(author);
        this.userDocumentMap.put(author.getUserId(), document);
        return true;
    }

    public void updateDocument(Document document, Integer userId){
        this.documentMap.put(document.getDocumentId(), document);
        this.userDocumentMap.put(userId, document);
    }

    public boolean deleteDocument(Integer documentId) throws DocumentDoesNotExistException {
        if(!this.documentMap.containsKey(documentId)){
            throw new DocumentDoesNotExistException();
        }
        this.documentMap.remove(documentId);
        return true;
    }

    public Document getDocument(Integer documentId){
        return this.documentMap.get(documentId);
    }

    public Integer getLatestActiveVersion(Integer documentId) throws DocumentDoesNotExistException{
        if(!this.documentMap.containsKey(documentId)){
            throw new DocumentDoesNotExistException();
        }
        Document document = this.documentMap.get(documentId);
        return document.getLatestVersionId();
    }

    public Document revertToPreviousVersion(Integer documentId, Integer versionId) throws DocumentDoesNotExistException{
        if(!this.documentMap.containsKey(documentId)){
            throw new DocumentDoesNotExistException();
        }
        Document currDoc = this.documentMap.get(documentId);
        List<Document> versionList = currDoc.getVersions();
        Document versionDoc = versionList.get(versionId-1);
        int index = versionId;
        List<Document> followingVersion = new ArrayList<>();
        for(int i = index; i<versionList.size(); i++){
            followingVersion.add(versionList.get(i));
        }
        versionDoc.getVersions().addAll(followingVersion);
        this.documentMap.put(documentId, versionDoc);
        return versionDoc;
    }
}
