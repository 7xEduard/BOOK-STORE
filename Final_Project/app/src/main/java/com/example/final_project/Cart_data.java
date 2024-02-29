package com.example.final_project;

import androidx.annotation.NonNull;

public class Cart_data {

    private int qun;
    private int id;
    private String name;
    private String category;
    private int img;
//////////////////////////////////////////////////////////
    public void setQun(int qun) {
        this.qun = qun;
    }

    public int getQun() {
        return qun;
    }
/////////////////////////////////////////////////////////


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart_data(int id, String name, String category, int img,int qun) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.img = img;
        this.qun=qun;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
