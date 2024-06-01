package com.example.phonepe.service;

import com.example.phonepe.models.User;

import java.util.List;

public interface DocumentManager {
    void createDocument(String text, Integer authorId, List<Integer>editAccessList, List<Integer> viewAccessList);
    void updateDocument(String text, Integer authorId, List<Integer> editAccessList, List<Integer> viewAccessList, Integer documentId);
    void deleteDocument(String userName, String password, Integer documentId);
    void getActiveVersion(Integer documentId);
    void revertToPreviousVersion(Integer documentId, String userName, String password, Integer versionId);
}
