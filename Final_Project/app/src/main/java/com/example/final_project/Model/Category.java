package com.example.final_project.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project.Book_Database;
import com.example.final_project.RecyclerViewInterface;

public abstract class Category {
    private Context c;
    private RecyclerViewInterface recyclerViewInterface;

    public Context getC() {
        return c;
    }

    public RecyclerViewInterface getRecyclerViewInterface() {
        return recyclerViewInterface;
    }



    public Category(Context c,RecyclerViewInterface recyclerViewInterface) {
        this.c = c;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public abstract void categoryType(LayoutInflater inflater, ViewGroup container, View view,Book_Database b);
}
