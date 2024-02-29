package com.example.final_project.Model;

import android.content.Context;

import com.example.final_project.Book_Database;

public interface MakeOrder {
    public abstract int buildOrder(Context context,Book_Database database, int book_id);
}
