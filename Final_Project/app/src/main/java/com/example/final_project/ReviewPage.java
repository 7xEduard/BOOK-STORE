package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Vector;

public class ReviewPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);
        Book_Database database =  Book_Database.getInstance(this);
        Cursor cursor6 = database.fetch_one_comment(getIntent().getExtras().getInt("id"));
        ArrayAdapter<String>aa=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        while(!cursor6.isAfterLast())
        {
            aa.add(cursor6.getString(2));
            cursor6.moveToNext();
        }
        ListView my_list = (ListView) this.findViewById(R.id.review_list);
        my_list.setAdapter(aa);
    }
}