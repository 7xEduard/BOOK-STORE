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

public class Romance extends Category {
    public Romance(Context c, RecyclerViewInterface recyclerViewInterface) {
        super(c, recyclerViewInterface);
    }

    @Override
    public void categoryType(LayoutInflater inflater, ViewGroup container, View view, Book_Database b) {
        RecyclerView history=view.findViewById(R.id.romance);
        history.setHasFixedSize(true);
        history.setLayoutManager(new LinearLayoutManager(super.getC(),LinearLayoutManager.HORIZONTAL,false));
        topBooksAdaptor h=new topBooksAdaptor(super.getC(), b.fetchOneBook("ROMANCE"), super.getRecyclerViewInterface(),"ROMANCE");
        history.setAdapter(h);
    }
}
