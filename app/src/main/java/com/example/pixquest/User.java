package com.example.pixquest;

public class User {
    private String username, password, img;
    private int credit;

    public User(){}

    public User(String username, String password){
        this.username = username;
        this.password = password;
        credit = 0;
        img = "default.jpg";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
