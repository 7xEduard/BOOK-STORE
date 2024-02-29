package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Vector;

public class Edit_Book_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_page);

        Book_Database database =  Book_Database.getInstance(Edit_Book_Page.this);
        Vector<Cart_data> items = new Vector<Cart_data>();
        Category_Adaptor adapter = new Category_Adaptor(Edit_Book_Page.this,R.layout.activity_category_list,R.id.cat_name,items,database);

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
            Toast.makeText(Edit_Book_Page.this, "THERE ARE NO BOOKS HERE :(", Toast.LENGTH_SHORT).show();
        }

        ListView edit = (ListView) findViewById(R.id.edit_boook);
        edit.setAdapter(adapter);
        edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Edit_Book_Page.this, Edit_Field_Page.class);
                String data = adapterView.getItemAtPosition(i).toString();
                Cursor cursor2=database.fetchBooksByName(data);
                Cursor cursor3=database.fetch_writer_name(cursor2.getInt(0));
                Cursor cursor4=database.fetch_author_data(cursor3.getString(0));
                intent.putExtra("bookId",cursor2.getInt(0));
                intent.putExtra("bookName", cursor2.getString(1));
                intent.putExtra("bookCategory",cursor2.getString(2));
                intent.putExtra("publishDate",cursor2.getString(3));
                intent.putExtra("description",cursor2.getString(4));
                intent.putExtra("language",cursor2.getString(5));
                intent.putExtra("chapters",cursor2.getString(6));
                intent.putExtra("pages",cursor2.getString(7));
                intent.putExtra("parts",cursor2.getString(8));
                intent.putExtra("counter",cursor2.getString(9));
                intent.putExtra("bookLink",cursor2.getString(11));
                intent.putExtra("imageLink", cursor2.getInt(10));
                intent.putExtra("authorName",cursor4.getString(0));
                intent.putExtra("authorNationality",cursor4.getString(1));
                intent.putExtra("noOfBooks",cursor4.getString(2));
                intent.putExtra("price",cursor2.getString(12));
                intent.putExtra("edition",cursor2.getString(13));
                intent.putExtra("availability",cursor2.getString(14));
                intent.putExtra("popularity",cursor2.getString(15));
                startActivity(intent);
            }
        });
        Animation animation = AnimationUtils.loadAnimation(Edit_Book_Page.this,R.anim.anim_one);
        edit.setAnimation(animation);
    }
}