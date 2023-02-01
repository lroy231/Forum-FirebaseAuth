package com.example.inclass08;

import java.io.Serializable;
import java.util.Date;

public class Forum implements Serializable {
    String author;
    String title;
    String forum;
    Date date;



    public Forum(String author, String title, String forum, Date date) {
        this.author = author;
        this.title = title;
        this.forum = forum;
        this.date = date;

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForum() {
        return forum;
    }

    public void setPost(String post) {
        this.forum = forum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "user='" + author + '\'' +
                ", title='" + title + '\'' +
                ", post='" + forum + '\'' +
                ", date=" + date +
                '}';
    }
}
