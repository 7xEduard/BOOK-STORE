package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Vector;

public class Admin_Review extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_review);

        Book_Database database =  Book_Database.getInstance(Admin_Review.this);
        Vector<Cart_data> items = new Vector<Cart_data>();
        Category_Adaptor adapter = new Category_Adaptor(Admin_Review.this,R.layout.activity_category_list,R.id.cat_name,items,database);

        Cursor cursor = database.fetchBooks();
        items.clear();
        if(cursor!=null) {
            while (!cursor.isAfterLast()) {

                //cursor1 = database.fetch_one_book(cursor.getInt(1));
                items.add(new Cart_data(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(10),1));

                cursor.moveToNext();
            }

            cursor.moveToFirst();
            //adapter.notifyDataSetChanged();
        }
        else
        {
            //adapter.notifyDataSetChanged();
            Toast.makeText(Admin_Review.this, "THERE ARE NO BOOKS HERE :(", Toast.LENGTH_SHORT).show();
        }
        ListView review = (ListView) findViewById(R.id.admin_review_list);
        review.setAdapter(adapter);
        review.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Admin_Review.this, Admin_Book_Review.class);
                String data = adapterView.getItemAtPosition(i).toString();
                Cursor cursor2=database.fetchBooksByName(data);
                intent.putExtra("id",cursor2.getInt(0));
                startActivity(intent);
            }
        });

    }
}