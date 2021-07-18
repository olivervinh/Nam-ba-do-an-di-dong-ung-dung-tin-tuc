package com.example.myapplication34;

public class BinhLuan {
   private int Id;
    private String Content;
    private int IdUser;
    public int IdArticle;

    public BinhLuan() {
    }

    public BinhLuan(int id, String content, int idUser, int idArticle) {
        Id = id;
        Content = content;
        IdUser = idUser;
        IdArticle = idArticle;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public int getIdArticle() {
        return IdArticle;
    }

    public void setIdArticle(int idArticle) {
        IdArticle = idArticle;
    }
}
