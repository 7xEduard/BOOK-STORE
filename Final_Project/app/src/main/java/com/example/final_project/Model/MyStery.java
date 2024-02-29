package com.example.final_project.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.AdaptorPack.topBooksAdaptor;
import com.example.final_project.Book_Database;
import com.example.final_project.R;
import com.example.final_project.RecyclerViewInterface;

public class MyStery extends Category {
    public MyStery(Context c, RecyclerViewInterface recyclerViewInterface) {
        super(c, recyclerViewInterface);
    }

    @Override
    public void categoryType(LayoutInflater inflater, ViewGroup container, View view, Book_Database b) {
        RecyclerView mystery=view.findViewById(R.id.myStery);
        mystery.setHasFixedSize(true);
        mystery.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        topBooksAdaptor m =new topBooksAdaptor(view.getContext(), b.fetchOneBook("MYSTERY"), super.getRecyclerViewInterface(),"MYSTERY");
        mystery.setAdapter(m);
    }
}
