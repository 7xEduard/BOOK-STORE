package com.example.final_project.Model;

import android.content.Context;
import android.widget.Toast;

import com.example.final_project.Book_Database;

public class AddOrder implements MakeOrder{
    String email;

    public AddOrder(String email) {
        this.email = email;
    }

    @Override
    public int buildOrder(Context context, Book_Database database, int book_id) {
        boolean add = database.add_order(email,book_id,0);
        if(add)
        {
            return 1;

        }
        return 0;
    }
}
