package com.adamzfc.androidbase.test.sqlite;

/**
 * Created by adamzfc on 4/27/17.
 */

public class Post {
    public User user;
    public String text;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Post{" +
                "user=" + user +
                ", text='" + text + '\'' +
                '}';
    }
}
