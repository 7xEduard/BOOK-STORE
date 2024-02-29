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

public class Fantasy extends Category{
    public Fantasy(Context c, RecyclerViewInterface recyclerViewInterface) {
        super(c, recyclerViewInterface);
    }

    @Override
    public void categoryType(LayoutInflater inflater, ViewGroup container, View view, Book_Database b) {
        RecyclerView fantasy=view.findViewById(R.id.fantasy);
        fantasy.setHasFixedSize(true);
        fantasy.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        topBooksAdaptor f=new topBooksAdaptor(view.getContext(),b.fetchOneBook("FANTASY"), super.getRecyclerViewInterface(),"FANTASY");
        fantasy.setAdapter(f);
    }

}
