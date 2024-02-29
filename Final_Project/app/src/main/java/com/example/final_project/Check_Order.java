package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import java.util.Vector;

public class Check_Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);

        Book_Database database =  Book_Database.getInstance(Check_Order.this);

        Vector<Cart_data> items = new Vector<Cart_data>();
        Cursor cursor = database.fetch_orders_for_one_user(getIntent().getExtras().getString("email"));
        while(!cursor.isAfterLast())
        {
            Cursor cursor1 = database.fetch_one_book(cursor.getInt(1));
            items.add(new Cart_data(cursor1.getInt(0),cursor1.getString(1),cursor1.getString(2),cursor1.getInt(10),1));
            //items.add(cursor.getString(0));

            cursor.moveToNext();
        }
        //cursor.moveToFirst();
        ListView my_list = (ListView) findViewById(R.id.order_list_order);
        Order_Adapter adapter = new Order_Adapter(Check_Order.this,R.layout.activity_orders_list,R.id.ord_name,items,database);
        my_list.setAdapter(adapter);
        Animation animation = AnimationUtils.loadAnimation(Check_Order.this,R.anim.anim_one);
        my_list.setAnimation(animation);
    }
}