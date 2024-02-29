package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Vector;

public class History_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);

        Book_Database database =  Book_Database.getInstance(History_Page.this);
        Vector<Cart_data> items = new Vector<Cart_data>();
        Category_Adaptor adapter = new Category_Adaptor(History_Page.this,R.layout.activity_category_list,R.id.cat_name,items,database);

        Cursor cc = database.fetch_flag();
        Cursor cursor = database.fetch_all_history(cc.getString(2));
        items.clear();
        if(cursor!=null) {
            while (!cursor.isAfterLast()) {

                Cursor cursor1 = database.fetch_one_book(cursor.getInt(1));
                items.add(new Cart_data(cursor1.getInt(0), cursor1.getString(1), cursor1.getString(2), cursor1.getInt(10),1));

                cursor.moveToNext();
            }

            cursor.moveToFirst();
            //adapter.notifyDataSetChanged();
        }
        else
        {
            //adapter.notifyDataSetChanged();
            Toast.makeText(History_Page.this, "THERE ARE NO BOOKS HERE :(", Toast.LENGTH_SHORT).show();
        }
        ListView history = (ListView) findViewById(R.id.history_list);
        history.setAdapter(adapter);
    }
}