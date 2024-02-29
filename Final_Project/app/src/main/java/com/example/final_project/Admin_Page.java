package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Admin_Page extends AppCompatActivity {

    //Book_Database database =  Book_Database.getInstance(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {


            case R.id.logout_btn:
            {
                Intent intent = new Intent(Admin_Page.this,Login_and_Signup.class);
                startActivity(intent);
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        LinearLayout add = (LinearLayout) findViewById(R.id.addd_btn);
        LinearLayout del = (LinearLayout) findViewById(R.id.delete_btn);
        LinearLayout edit = (LinearLayout) findViewById(R.id.edit_btn);
        LinearLayout order = (LinearLayout) findViewById(R.id.order_btn);
        LinearLayout review = (LinearLayout) findViewById(R.id.reviewww_btn);
        LinearLayout stat = (LinearLayout) findViewById(R.id.stat_btn);

        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replace = new Intent(Admin_Page.this , Stat_Page.class);
                startActivity(replace);
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replace = new Intent(Admin_Page.this , Admin_Review.class);
                startActivity(replace);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replace = new Intent(Admin_Page.this , Add_Book_Page.class);
                startActivity(replace);

            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replace = new Intent(Admin_Page.this , Delete_Category_Page.class);
                startActivity(replace);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replace = new Intent(Admin_Page.this , Edit_Book_Page.class);
                startActivity(replace);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replace = new Intent(Admin_Page.this , User_Order.class);
                startActivity(replace);
            }
        });
    }
}