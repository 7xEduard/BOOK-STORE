package com.example.final_project;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Signup_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signup_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Signup_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Signup_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Signup_Fragment newInstance(String param1, String param2) {
        Signup_Fragment fragment = new Signup_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        Book_Database database = Book_Database.getInstance(getActivity());

        EditText email = (EditText) view.findViewById(R.id.signup_page_mail);
        EditText username = (EditText) view.findViewById(R.id.signup_page_username);
        EditText birthdate = (EditText) view.findViewById(R.id.signup_page_birthdate);
        EditText address = (EditText) view.findViewById(R.id.signup_page_address);
        EditText phone = (EditText) view.findViewById(R.id.signup_page_phone);
        EditText pass1 = (EditText) view.findViewById(R.id.signup_page_pass1);
        EditText pass2 = (EditText) view.findViewById(R.id.signup_page_pass2);
        Button signup = (Button) view.findViewById(R.id.signup_page_btn);
        Spinner spinner = (Spinner) view.findViewById(R.id.signup_page_spinner);
        EditText answer = (EditText) view.findViewById(R.id.signup_page_answer);

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
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("")
                        ||username.getText().toString().equals("")
                        ||birthdate.getText().toString().equals("")
                        ||address.getText().toString().equals("")
                        ||phone.getText().toString().equals("")
                        ||pass1.getText().toString().equals("")
                        ||pass2.getText().toString().equals("")
                        ||answer.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "EMPTY FIELDS !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean check = database.check_user_email(email.getText().toString());
                    if(check == true)
                    {
                        Toast.makeText(getActivity(), "THIS EMAIL HAS TAKEN !", Toast.LENGTH_SHORT).show();
                        email.setText("");

                    }
                    else
                    {
                        if(!pass1.getText().toString().equals(pass2.getText().toString()))
                        {
                            Toast.makeText(getActivity(), "2 PASSWORD ARE NOT THE SAME !", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(getActivity(), "PHONE NUMBER MUST BE 11 DIGITS.", Toast.LENGTH_SHORT).show();
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
                                                    Toast.makeText(getActivity(), "THIS USERNAME HAS TAKEN !", Toast.LENGTH_SHORT).show();
                                                    username.setText("");

                                                }
                                                else
                                                {
                                                    if((cyear[0]-byear[0]>18))
                                                    {
                                                        boolean add = database.insert_user_data(email.getText().toString(),
                                                                pass1.getText().toString(),username.getText().toString(),
                                                                address.getText().toString(),phone.getText().toString(),birthdate.getText().toString(),question[0],answer.getText().toString());
                                                        if(add == true)
                                                        {
                                                            //database.insertBooks(29,"test","OTHERS","2019","I was three when I watched my mother die. Now Im back in Muckle Cove digging into her murder. They say coming home is never easy, but what Im finding goes so much deeper than anyone could have imagined.","ENGLISH",24,189,1,14,R.drawable.romance_5,"https://drive.google.com/file/d/1U-Mwh3jJx2kMunpcSxHuMBT1GszKTMnW/view?usp=sharing",20.5,"4",1,"Popular");
                                                            //database.insertWrite("T.K. Eldridge",29);
                                                            Toast.makeText(getActivity(), "EMAIL ADDED SECCESSFULLY !", Toast.LENGTH_SHORT).show();
                                                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_signup_fragment,new Login_Fragment()).commit();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(getActivity(), "EMAIL NOT ADDED !", Toast.LENGTH_SHORT).show();
                                                            email.setText("");
                                                            pass1.setText("");
                                                            pass2.setText("");
                                                            username.setText("");
                                                            birthdate.setText("");
                                                            address.setText("");
                                                            phone.setText("");
                                                        }
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(getActivity(), "SORRY ONLY PEOPLE HAVE 18 OR OLDER CAN SIGNUP :(", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }
                                            else
                                            {
                                                Toast.makeText(getActivity(), "THERE IS NO SERVICE PROVIDER WITH THIS NUMBER", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(getActivity(), "PHONE NUMBER MUST START WITH 01.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), "PHONE NUMBER MUST START WITH 01.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                            else
                            {
                                Toast.makeText(getActivity(), "INVALID EMAIL ADDRESS !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
        return view;
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
            Toast.makeText(getActivity(), "Invalid Email Address !", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}