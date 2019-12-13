package com.example.pixquest;

public class Items {

    private String id;
    private String img;
    private String itemname;
    //private int price;
    private String owner;

    public Items(){};
    public Items(String id, String img, String itemname, String owner){
        this.id = id;
        this.img = img;
        this.itemname = itemname;
        //this.price = price;
        this.owner = owner;
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
