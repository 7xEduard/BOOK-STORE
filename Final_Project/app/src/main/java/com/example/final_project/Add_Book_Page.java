package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Add_Book_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_page);

        Button add = (Button) findViewById(R.id.add_book_btn);
        EditText name = (EditText) findViewById(R.id.add_book_name);
        EditText date = (EditText) findViewById(R.id.add_book_date);
        EditText desc = (EditText) findViewById(R.id.add_book_desc);
        EditText lang = (EditText) findViewById(R.id.add_book_lang);
        EditText chap = (EditText) findViewById(R.id.add_book_chapter);
        EditText page = (EditText) findViewById(R.id.add_book_pages);
        EditText part = (EditText) findViewById(R.id.add_book_parts);
        EditText counter = (EditText) findViewById(R.id.add_book_counter);
        EditText price = (EditText) findViewById(R.id.add_book_price);
        EditText edittion = (EditText) findViewById(R.id.add_book_edit);
        Spinner cat = (Spinner) findViewById(R.id.add_book_category);
        Spinner pob = (Spinner) findViewById(R.id.add_book_pob);
        final String[] cat_txt = {"OTHERS"};
        final String[] pob_txt = {"Popular"};
        pob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pob_txt[0] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat_txt[0] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Book_Database database = Book_Database.getInstance(Add_Book_Page.this);
                Cursor cc = database.fetch_counter_flag();
                database.insertBooks(cc.getInt(1),name.getText().toString(),cat_txt[0],date.getText().toString(),desc.getText().toString(),lang.getText().toString(),Integer.parseInt(chap.getText().toString()),Integer.parseInt(page.getText().toString()),Integer.parseInt(part.getText().toString()),Integer.parseInt(counter.getText().toString()),R.drawable.shopping_bag,"",Double.parseDouble(price.getText().toString()),edittion.getText().toString(),1,pob_txt[0]);
                database.insertWrite("OTHER",cc.getInt(1));
                database.update_counter_flag();
                Toast.makeText(Add_Book_Page.this, "BOOK ADDED SUCCESSFULLY.", Toast.LENGTH_SHORT).show();
                name.setText("");
                date.setText("");
                desc.setText("");
                lang.setText("");
                chap.setText("");
                page.setText("");
                part.setText("");
                counter.setText("");
                price.setText("");
                edittion.setText("");
            }
        });

    }
}