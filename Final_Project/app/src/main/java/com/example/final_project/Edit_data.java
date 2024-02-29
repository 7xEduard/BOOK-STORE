package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Edit_data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        Book_Database database =  Book_Database.getInstance(this);
        Cursor cursor = database.fetch_flag();
        EditText email = (EditText) findViewById(R.id.edit_page_mail);
        EditText username = (EditText) findViewById(R.id.edit_page_userName);
        EditText birthdate = (EditText) findViewById(R.id.edit_page_birthdate);
        EditText address = (EditText) findViewById(R.id.edit_page_address);
        EditText phone = (EditText) findViewById(R.id.edit_page_phone);
        EditText pass1 = (EditText) findViewById(R.id.edit_page_pass1);
        EditText pass2 = (EditText) findViewById(R.id.edit_page_pass2);
        Button save = (Button) findViewById(R.id.edit_page_btn);
        Spinner spinner = (Spinner) findViewById(R.id.edit_page_spinner);
        EditText answer = (EditText) findViewById(R.id.edit_page_answer);

        final int[] cyear = {0};
        final int[] byear = {0};

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                cyear[0] = calendar.get(Calendar.YEAR);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                String selectedDate = selectedYear + "/" + (selectedMonth + 1) + "/" + selectedDay;
                                byear[0] = selectedYear;
                                birthdate.setText(selectedDate);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();

            }
        });

        final String[] question = {""};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                question[0] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("")||pass1.getText().toString().equals("")
                        ||pass2.getText().toString().equals("")||birthdate.getText().toString().equals("")
                        ||answer.getText().toString().equals(""))
                {
                    Toast.makeText(Edit_data.this, "EMPTY FIELDS !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.remove_user(cursor.getString(2));
                    boolean check = database.check_user_email(email.getText().toString());
                    if(check == true)
                    {
                        Toast.makeText(Edit_data.this, "THIS EMAIL HAS TAKEN !", Toast.LENGTH_SHORT).show();
                        email.setText("");

                    }
                    else
                    {
                        if(!pass1.getText().toString().equals(pass2.getText().toString()))
                        {
                            Toast.makeText(Edit_data.this, "2 PASSWORD ARE NOT THE SAME !", Toast.LENGTH_SHORT).show();
                            pass1.setText("");
                            pass2.setText("");
                        }
                        else
                        {
                            boolean c = validate(email);
                            if(c)
                            {
                                String num = phone.getText().toString();
                                if(num.length()>11||num.length()<11)
                                {
                                    Toast.makeText(getApplicationContext(), "PHONE NUMBER MUST BE 11 DIGITS.", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    char[] test = num.toCharArray();
                                    if(test[0]=='0')
                                    {
                                        if(test[1]=='1')
                                        {
                                            if(test[2]=='0'||test[2]=='1'||test[2]=='2'||test[2]=='5')
                                            {
                                                boolean checkUser = database.check_userName(username.getText().toString());
                                                if(checkUser == true)
                                                {
                                                    Toast.makeText(getApplicationContext(), "THIS USERNAME HAS TAKEN !", Toast.LENGTH_SHORT).show();
                                                    username.setText("");

                                                }
                                                else
                                                {
                                                    if((cyear[0]-byear[0]>18)) {
                                                        Cursor cursor7 = database.fetch_flag();
                                                        database.remove_local();
                                                        database.inser_local(1, 0, cursor7.getString(2), 0);
                                                        boolean add = database.insert_user_data(email.getText().toString(),
                                                                pass1.getText().toString(), username.getText().toString(),
                                                                address.getText().toString(), phone.getText().toString(),birthdate.getText().toString(),question[0],answer.getText().toString());
                                                        if (add == true) {
                                                            Toast.makeText(Edit_data.this, "DATA UPDATED SUCCESSFULLY !", Toast.LENGTH_SHORT).show();
                                                            Book_Database database = Book_Database.getInstance(getApplicationContext());
                                                            database.remove_local();
                                                            database.inser_local(1, 1, email.getText().toString(), 0);
                                                            TextView mail = (TextView) findViewById(R.id.profile_email_label);
                                                            TextView email = (TextView) findViewById(R.id.profile_email_edit);
                                                            Cursor cursor6 = database.fetch_flag();
                                                            mail.setText(cursor6.getString(2));
                                                            email.setText(cursor6.getString(2));
                                                            finish();
                                                        } else {
                                                            Toast.makeText(Edit_data.this, "DATA NOT UPDATED !", Toast.LENGTH_SHORT).show();
                                                            email.setText("");
                                                            pass1.setText("");
                                                            pass2.setText("");
                                                            username.setText("");
                                                            address.setText("");
                                                            phone.setText("");
                                                        }
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(Edit_data.this, "SORRY YOUR BIRTH DATE IS INCORRECT :(", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), "THERE IS NO SERVICE PROVIDER WITH THIS NUMBER", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "PHONE NUMBER MUST START WITH 01.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "PHONE NUMBER MUST START WITH 01.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "INVALID EMAIL ADDRESS !", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
            }
        });
    }
    private boolean validate(EditText email)
    {
        String input = email.getText().toString();
        if(Patterns.EMAIL_ADDRESS.matcher(input).matches())
        {
            return true;
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Invalid Email Address !", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}