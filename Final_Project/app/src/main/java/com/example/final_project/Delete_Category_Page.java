package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Vector;

public class Delete_Category_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category_page);

        Book_Database database =  Book_Database.getInstance(Delete_Category_Page.this);

        Vector<Cart_data> items = new Vector<Cart_data>();
        Spinner cat = (Spinner) findViewById(R.id.cat_drop);
        Cursor cursor = database.fetch_one_category("HORROR");
        while(!cursor.isAfterLast())
        {
            //cursor1 = database.fetch_one_book(cursor.getInt(1));
            items.add(new Cart_data(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(10),1));

            cursor.moveToNext();
        }
        cursor.moveToFirst();
        ListView my_list = (ListView) findViewById(R.id.cat_list);
        Category_Adaptor adapter = new Category_Adaptor(Delete_Category_Page.this,R.layout.activity_category_list,R.id.cat_name,items,database);
        my_list.setAdapter(adapter);
        Button delete = (Button) findViewById(R.id.delete_all_cat);
        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(Delete_Category_Page.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                Cursor cursor = database.fetch_one_category(adapterView.getItemAtPosition(i).toString());
                items.clear();
                if(cursor!=null) {
                    while (!cursor.isAfterLast()) {

                        //cursor1 = database.fetch_one_book(cursor.getInt(1));
                        items.add(new Cart_data(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(10),1));

                        cursor.moveToNext();
                    }

                    cursor.moveToFirst();
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    adapter.notifyDataSetChanged();
                    Toast.makeText(Delete_Category_Page.this, "THIS CATEGORY HAS BEEN DELETED :(", Toast.LENGTH_SHORT).show();
                }

//                ListView my_list = (ListView) view.findViewById(R.id.cat_list);
//                ArrayAdapter<Cart_data> aa=new ArrayAdapter<Cart_data>(getApplicationContext(), R.layout.activity_category_list,items);
//
//                //Cart_Adapter adapter = new Cart_Adapter(Delete_Category_Page.this,R.layout.activity_cart_list,R.id.cart_name,items,database);
//                my_list.setAdapter(aa);


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Delete_Category_Page.this);
                        builder.setTitle("Delete")
                                        .setMessage("Are you sure you want to delete this from database")
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int id) {
                                                        database.remove_category(adapterView.getItemAtPosition(i).toString());
                                                        Toast.makeText(Delete_Category_Page.this, "CATEGORY HAS BEEN DELETED:(", Toast.LENGTH_SHORT).show();
                                                        adapter.notifyDataSetChanged();
                                                        dialogInterface.dismiss();

                                                    }
                                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();

                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();

//                        database.remove_category(adapterView.getItemAtPosition(i).toString());
//                        Toast.makeText(Delete_Category_Page.this, "CATEGORY HAS BEEN DELETED:(", Toast.LENGTH_SHORT).show();
//                        adapter.notifyDataSetChanged();



                    }
                });

                my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(Delete_Category_Page.this);
                        builder.setTitle("Delete")
                                .setMessage("Are you sure you want to delete this from database")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int id) {
                                        database.remove_one_book(items.get(i).getId());
                                        Toast.makeText(Delete_Category_Page.this, "BOOK HAS BEEN DELETED:(", Toast.LENGTH_SHORT).show();
                                        adapter.notifyDataSetChanged();
                                        dialogInterface.dismiss();

                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();

                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();


//                        database.remove_one_book(items.get(i).getId());
//                        adapter.notifyDataSetChanged();
                    }
                });
                Animation animation = AnimationUtils.loadAnimation(Delete_Category_Page.this,R.anim.anim_one);
                my_list.setAnimation(animation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void showDialog()
    {

    }
}