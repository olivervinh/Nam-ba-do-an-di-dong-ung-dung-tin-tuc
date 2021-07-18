package com.example.myapplication34;

public class BinhLuanServerToClientModel {
   private int id;
    private int idArticle;
    private String content;
    private String userName;

    public BinhLuanServerToClientModel() {
    }

    public BinhLuanServerToClientModel(int id, int idArticle, String content, String userName) {
        this.id = id;
        this.idArticle = idArticle;
        this.content = content;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
