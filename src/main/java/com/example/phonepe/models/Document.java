package com.example.phonepe.models;

import com.example.phonepe.models.enums.DocumentState;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Document {
    private Integer documentId;
    private List<Document> versions;
    private String text;
    private Integer pages;
    private User author;
    private List<Integer> editAccessList; // userId
    private List<Integer> viewAccessList; //userId
    private Integer latestVersionId;
    private boolean isPrivate;
    private DocumentState documentState;

    public Document(){
        versions = new ArrayList<>();
        editAccessList = new ArrayList<>();
        viewAccessList = new ArrayList<>();
        pages = 1;
        this.isPrivate = false;
        this.documentState = DocumentState.PUBLISHED;
        this.latestVersionId = 1;
    }

    public Document(List<Document> versions, String text, User author, List<Integer> editAccessList, List<Integer> viewAccessList, Integer documentId){
        this.versions = versions;
        this.text = text;
        this.author = author;
        this.editAccessList = editAccessList;
        this.viewAccessList = viewAccessList;
        this.pages = 1;
        this.isPrivate = false;
        this.documentState = DocumentState.PUBLISHED;
        this.documentId = documentId;
    }
}
