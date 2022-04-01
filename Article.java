package com.esprit.myapp.entities;

public class Article {
    private int id,views;
    private String title,content,category;

    public Article() {

    }

    public Article(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Article(int views, String title, String content, String category) {
        this.views = views;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Article(int id, int views, String title, String content, String category) {
        this.id = id;
        this.views = views;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", views=" + views +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
