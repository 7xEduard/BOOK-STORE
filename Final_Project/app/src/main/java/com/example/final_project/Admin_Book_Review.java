package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;

public class Admin_Book_Review extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book_review);
        Book_Database database =  Book_Database.getInstance(this);
        Cursor cursor6 = database.fetch_one_comment(getIntent().getExtras().getInt("id"));
        Cursor ccc = database.fetch_one_rate(getIntent().getExtras().getInt("id"));
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        float av = 0.0F;
        int i = 0;
        while(!cursor6.isAfterLast())
        {
            aa.add(cursor6.getString(2));
            cursor6.moveToNext();
        }
        while (!ccc.isAfterLast())
        {
            av += ccc.getInt(2);
            ccc.moveToNext();
            i++;
        }
        float rate = av/i;
        RatingBar bar = (RatingBar) findViewById(R.id.avRate_review);
        bar.setRating(rate);
        ListView my_list = (ListView) this.findViewById(R.id.book_review_details);
        my_list.setAdapter(aa);


    }
}