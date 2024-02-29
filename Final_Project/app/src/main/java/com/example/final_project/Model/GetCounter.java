package com.example.final_project.Model;

import android.content.Context;
import android.database.Cursor;

import com.example.final_project.Book_Database;

public class GetCounter implements MakeOrder {


    @Override
    public int buildOrder(Context context, Book_Database database, int book_id) {
        Cursor cursor = database.get_counter(book_id);
        if(cursor!=null)
        {
            if(cursor.getInt(0)>0)
            {
                return cursor.getInt(0);
            }
        }
        return 0;
    }
}
