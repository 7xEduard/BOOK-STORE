package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    Book_Database database =  Book_Database.getInstance(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.Profile:
            {
                Intent intent = new Intent(MainActivity.this,Profile.class);
                startActivity(intent);
                break;
            }
            case R.id.about_option:
            {
                Intent intent = new Intent(MainActivity.this,about.class);
                startActivity(intent);
                break;
            }
            case R.id.share_option:
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Download Book Strore Program For More Fantastic Books .\n\n link"+getPackageName());
                startActivity(Intent.createChooser(intent,"Share With ."));
                break;
            }
            case R.id.support_option:
            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"+"paula.a.pham@gmail.com"));
                startActivity(intent);
                break;
            }
            case R.id.logout_btn:
            {
                Cursor cursor7 = database.fetch_flag();
                database.remove_local();
                database.inser_local(1,0,cursor7.getString(2),0);
                Intent intent = new Intent(MainActivity.this,Login_and_Signup.class);
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
        setContentView(R.layout.activity_main);




        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new Home_Fragment()).commit();
        BottomNavigationView bottom_bar = (BottomNavigationView) findViewById(R.id.bottom_nav);




        //select botton action
        bottom_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment new_fragment = null;
                replace_fragment(new_fragment , item);
                return true;
            }
        });




    }
    private void replace_fragment(Fragment new_fragment , @NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.home_button:
            {
                new_fragment = new Home_Fragment();
                break;
            }
            case R.id.cart_button:
            {
                new_fragment = new Cart_Fragment();
                break;
            }
            case R.id.search_button:
            {
                new_fragment = new Searsh_Fragment();
                break;
            }

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,new_fragment).commit();
    }
}