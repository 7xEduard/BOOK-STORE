package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Edit_Field_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_field_page);

        Button edit = (Button) findViewById(R.id.edit_book_btn);
        EditText name = (EditText) findViewById(R.id.edit_book_name);
        EditText date = (EditText) findViewById(R.id.edit_book_date);
        EditText desc = (EditText) findViewById(R.id.edit_book_desc);
        EditText lang = (EditText) findViewById(R.id.edit_book_lang);
        EditText chap = (EditText) findViewById(R.id.edit_book_chapter);
        EditText page = (EditText) findViewById(R.id.edit_book_pages);
        EditText part = (EditText) findViewById(R.id.edit_book_parts);
        EditText counter = (EditText) findViewById(R.id.edit_book_counter);
        EditText price = (EditText) findViewById(R.id.edit_book_price);
        EditText edittion = (EditText) findViewById(R.id.edit_book_edit);
        Spinner cat = (Spinner) findViewById(R.id.edit_book_category);
        Spinner pob = (Spinner) findViewById(R.id.edit_book_pob);
        final String[] cat_txt = {getIntent().getExtras().getString("bookCategory")};
        final String[] pob_txt = {"Popular"};
        name.setText(getIntent().getExtras().getString("bookName"));
        date.setText(getIntent().getExtras().getString("publishDate"));
        desc.setText(getIntent().getExtras().getString("description"));
        lang.setText(getIntent().getExtras().getString("language"));
        chap.setText(getIntent().getExtras().getString("chapters"));
        page.setText(getIntent().getExtras().getString("pages"));
        part.setText(getIntent().getExtras().getString("parts"));
        counter.setText(getIntent().getExtras().getString("counter"));
        price.setText(getIntent().getExtras().getString("price"));
        edittion.setText(getIntent().getExtras().getString("edition"));
        String caat = getIntent().getExtras().getString("bookCategory");
        if(caat.equals("HORROR"))
        {
            cat.setSelection(0);
        }
        else if(caat.equals("FANTASY"))
        {
            cat.setSelection(1);
        }
        else if(caat.equals("MYSTERY"))
        {
            cat.setSelection(2);
        }
        else if(caat.equals("SCIENCE FICTION"))
        {
            cat.setSelection(3);
        }
        else if (caat.equals("ROMANCE"))
        {
            cat.setSelection(4);
        }
        else if(caat.equals("OTHERS"))
        {
            cat.setSelection(5);
        }
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
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book_Database database = Book_Database.getInstance(Edit_Field_Page.this);
                database.editBook(getIntent().getExtras().getInt("bookId"),name.getText().toString(),cat_txt[0],date.getText().toString(),desc.getText().toString(),lang.getText().toString(),Integer.parseInt(chap.getText().toString()),Integer.parseInt(page.getText().toString()),Integer.parseInt(part.getText().toString()),Integer.parseInt(counter.getText().toString()),getIntent().getExtras().getInt("imageLink"),getIntent().getExtras().getString("bookLink"),Double.parseDouble(price.getText().toString()),edittion.getText().toString(),1,pob_txt[0]);
                //database.insertWrite("OTHER",cc.getInt(1));
                //database.update_counter_flag();
                Toast.makeText(Edit_Field_Page.this, "BOOK Updated SUCCESSFULLY.", Toast.LENGTH_SHORT).show();
//
                finish();
            }
        });
    }
}