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

public class ScienceFiction extends Category {
    public ScienceFiction(Context c, RecyclerViewInterface recyclerViewInterface) {
        super(c, recyclerViewInterface);
    }

    @Override
    public void categoryType(LayoutInflater inflater, ViewGroup container, View view, Book_Database b) {
        RecyclerView scienceFiction=view.findViewById(R.id.scienceFiction);
        scienceFiction.setHasFixedSize(true);
        scienceFiction.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        topBooksAdaptor s=new topBooksAdaptor(view.getContext(), b.fetchOneBook("SCIENCE FICTION"), super.getRecyclerViewInterface(),"SCIENCE FICTION");
        scienceFiction.setAdapter(s);
    }
}
