package com.minami.memoapp.model;

public class User {

    public String email;
    public String password;
    public String nickname;

    public User(){

    }

    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}