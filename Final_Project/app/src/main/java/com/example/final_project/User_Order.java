package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Vector;

public class User_Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);
        Book_Database database =  Book_Database.getInstance(User_Order.this);

        Vector<String> items = new Vector<String>();
        Cursor cursor = database.fetch_all_user();
        while(!cursor.isAfterLast())
        {
            //cursor1 = database.fetch_one_book(cursor.getInt(1));
            items.add(cursor.getString(0));

            cursor.moveToNext();
        }
        cursor.moveToFirst();
        ListView my_list = (ListView) findViewById(R.id.user_list);
        ArrayAdapter<String> aa=new ArrayAdapter<String>(User_Order.this, android.R.layout.simple_list_item_1,items);
        my_list.setAdapter(aa);
        my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(User_Order.this, Check_Order.class);
                String data = adapterView.getItemAtPosition(i).toString();
                intent.putExtra("email",data);
//                Cursor cc = database.fetch_orders_for_one_user(data);
//                while(!cc.isAfterLast())
//                {
//
//                    Cursor cursor1 = database.fetch_one_book(cc.getInt(1));
//                    System.out.println(cursor1.getString(1));
//                    //items.add(new Cart_data(cursor1.getInt(0),cursor1.getString(1),cursor1.getString(2),cursor1.getInt(10)));
//                    //items.add(cursor.getString(0));
//
//                    cc.moveToNext();
//                }
                startActivity(intent);
                //Cursor cursor1 = database.fetch_orders_for_one_user(data);

            }
        });
        Animation animation = AnimationUtils.loadAnimation(User_Order.this,R.anim.anim_one);
        my_list.setAnimation(animation);

    }
}