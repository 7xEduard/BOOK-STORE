package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Book_Templete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_templete);
        Book_Database database =  Book_Database.getInstance(Book_Templete.this);
        TextView b_name = (TextView) findViewById(R.id.book_name);
        TextView cat = (TextView) findViewById(R.id.book_category);
        TextView price = (TextView) findViewById(R.id.price);
        TextView chapter = (TextView) findViewById(R.id.chapters);
        TextView pages = (TextView) findViewById(R.id.pages);
        TextView available = (TextView) findViewById(R.id.available);
        TextView desc = (TextView) findViewById(R.id.describtion);
        TextView a_name = (TextView) findViewById(R.id.author_name);
        TextView a_nat = (TextView) findViewById(R.id.nationality);
        TextView nob = (TextView) findViewById(R.id.nob);
        TextView b_lang = (TextView) findViewById(R.id.b_lang);
        ImageView img = (ImageView) findViewById(R.id.book_image);
        CheckBox cart_btn = (CheckBox) findViewById(R.id.add_to_cart);
        Button read = (Button) findViewById(R.id.read_btn);
        Button review_button = (Button) findViewById(R.id.reviewsBtn);
        EditText comment = (EditText)findViewById(R.id.writeReview);
        Button add_comment = (Button) findViewById(R.id.addReview_btn);
        EditText quantity = (EditText)findViewById(R.id.qun);
        RatingBar rate = (RatingBar) findViewById(R.id.rate);
        b_name.setText(getIntent().getExtras().getString("bookName"));
        cat.setText(getIntent().getExtras().getString("bookCategory"));
        price.setText(getIntent().getExtras().getString("price")+" $");
        chapter.setText(getIntent().getExtras().getString("chapters"));
        pages.setText(getIntent().getExtras().getString("pages"));
        b_lang.setText(getIntent().getExtras().getString("language"));
        String av_test = getIntent().getExtras().getString("availability");
        rate.setRating(getIntent().getExtras().getInt("rate"));
        if(av_test.equals("0"))
        {

            available.setText("Not Available");
            available.setTextColor(Color.RED);
        }
        else
        {
            available.setText("Available");
        }


        desc.setText(getIntent().getExtras().getString("description"));
        a_name.setText(getIntent().getExtras().getString("authorName"));
        a_nat.setText(getIntent().getExtras().getString("authorNationality"));
        nob.setText(getIntent().getExtras().getString("noOfBooks"));
        img.setImageResource(getIntent().getExtras().getInt("imageLink"));
        boolean check_cart = database.check_cart(getIntent().getExtras().getInt("bookId"));
        if(check_cart)
        {
            cart_btn.setChecked(true);
        }
        else
        {
            cart_btn.setChecked(false);
        }

        Cursor cursor = database.fetch_flag();
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                database.updateRate(cursor.getString(2),getIntent().getExtras().getInt("bookId"), (int) v);

            }
        });

        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cart_btn.isChecked())
                {
                    if(!av_test.equals("0")) {
                        if(!quantity.getText().toString().equals("")) {
                            if (Integer.parseInt(quantity.getText().toString()) <= 10) {
                                Cursor crc = database.get_sells(getIntent().getExtras().getInt("bookId"));
                                if(crc.getCount()>0)
                                {
                                    int newCount = crc.getInt(1)+Integer.parseInt(quantity.getText().toString());
                                    database.updateSells(getIntent().getExtras().getInt("bookId"),newCount);
                                }
                                else
                                {
                                    database.inser_sells(getIntent().getExtras().getInt("bookId"),1);
                                }
                                boolean add = database.insert_to_cart(cursor.getString(2), getIntent().getExtras().getInt("bookId"), Integer.parseInt(quantity.getText().toString()));
                                if (add) {
                                    Toast.makeText(Book_Templete.this, "BOOK ADDED TO CART !", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(Book_Templete.this, "BOOK NOT ADDED !", Toast.LENGTH_SHORT).show();
                                    cart_btn.setChecked(false);
                                }
                            } else {
                                Toast.makeText(Book_Templete.this, "SORRY, QUANTITY MUST BE AT MOST 10 COPIES.", Toast.LENGTH_SHORT).show();
                                cart_btn.setChecked(false);
                            }
                        }
                        else
                        {
                            Toast.makeText(Book_Templete.this, "EMPTY FIELD :(", Toast.LENGTH_SHORT).show();
                            cart_btn.setChecked(false);
                        }
                    }
                    else
                    {
                        Toast.makeText(Book_Templete.this, "SORRY, THIS BOOK NOT AVAILABLE NOW !", Toast.LENGTH_SHORT).show();
                        cart_btn.setChecked(false);
                    }
                }
                else
                {
                    if(database.check_cart(getIntent().getExtras().getInt("bookId")))
                    {

                        boolean remove = database.remove_cart(cursor.getString(2),getIntent().getExtras().getInt("bookId"));
                        if(remove)
                        {
                            Toast.makeText(Book_Templete.this, "BOOK REMOVED FROM CART !", Toast.LENGTH_SHORT).show();
                            cart_btn.setChecked(false);
                        }
                        else
                        {
                            Toast.makeText(Book_Templete.this, "BOOK NOT REMOVED FROM CART !", Toast.LENGTH_SHORT).show();
                            cart_btn.setChecked(true);
                        }
                    }
                }
            }
        });



        review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Book_Templete.this, ReviewPage.class);
                intent.putExtra("id",getIntent().getExtras().getInt("bookId"));
                startActivity(intent);

            }
        });
        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean add = database.add_review(cursor.getString(2),getIntent().getExtras().getInt("bookId"),comment.getText().toString());
                if(add)
                {
                    Toast.makeText(Book_Templete.this, "Comment Has been Added", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Book_Templete.this, "Failed Comment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedUrl(getIntent().getExtras().getString("bookLink"));
            }
        });
    }

    public void proceedUrl(String url)
    {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }


}