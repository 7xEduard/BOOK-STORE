package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forget_Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        EditText pass = (EditText) findViewById(R.id.reset_page_pass);
        EditText pass2 = (EditText) findViewById(R.id.reset_page_pass_confirm);
        Button reset = (Button) findViewById(R.id.reset_page_btn);
        Book_Database database = Book_Database.getInstance(Forget_Password.this);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pass.getText().toString().equals("")||pass2.getText().toString().equals(""))
                {
                    Toast.makeText(Forget_Password.this, "Empty Filed :(", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    if(pass.getText().toString().equals(pass2.getText().toString()))
                    {
                        boolean cc = database.updatePass(getIntent().getExtras().getString("email"),pass.getText().toString());
                        if(cc)
                        {
                            Toast.makeText(Forget_Password.this, "Password has been reset successfully :)", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Forget_Password.this,Login_and_Signup.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Forget_Password.this, "Error Occurred :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Forget_Password.this, "2 Password not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}