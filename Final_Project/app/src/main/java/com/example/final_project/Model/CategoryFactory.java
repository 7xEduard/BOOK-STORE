package com.example.final_project.Model;

import android.content.Context;

import com.example.final_project.RecyclerViewInterface;

public class CategoryFactory {
    private Context c;
    private RecyclerViewInterface recyclerViewInterface;
    public CategoryFactory(Context c,RecyclerViewInterface recyclerViewInterface) {
        this.c = c;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public CategoryFactory() {
    }
    public Category getCategoryView(String categoryType)
    {
        switch (categoryType)
        {
            case "Fantasy":
            {
                return new Fantasy(c,recyclerViewInterface);
            }
            case "Horror":
            {
                return new Horror(c,recyclerViewInterface);
            }
            case "MyStery":
            {
                return new MyStery(c,recyclerViewInterface);
            }
            case "Romance":
            {
                return new Romance(c,recyclerViewInterface);
            }
            case "ScienceFiction":
            {
                return new ScienceFiction(c,recyclerViewInterface);
            }
            case "OTHERS":
            {
                return new Others(c,recyclerViewInterface);
            }
            default:
            {
                return null;
            }
        }
    }
}
