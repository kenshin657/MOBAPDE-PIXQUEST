package com.example.pixquest;

public class User {
    private String username, password, img;
    private int credit, singlecompleted, dailycompleted, weeklycompleted;

    public User(){}

    public User(String username, String password){
        this.username = username;
        this.password = password;
        credit = 0;
        img = "default.jpg";
        singlecompleted = 0;
        dailycompleted = 0;
        weeklycompleted = 0;
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

    public int getDailycompleted() {
        return dailycompleted;
    }

    public int getSinglecompleted() {
        return singlecompleted;
    }

    public int getWeeklycompleted() {
        return weeklycompleted;
    }

    public void setDailycompleted(int dailycompleted) {
        this.dailycompleted = dailycompleted;
    }

    public void setSinglecompleted(int singlecompleted) {
        this.singlecompleted = singlecompleted;
    }

    public void setWeeklycompleted(int weeklycompleted) {
        this.weeklycompleted = weeklycompleted;
    }
}
