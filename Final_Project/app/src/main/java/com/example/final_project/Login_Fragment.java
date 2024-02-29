package com.example.final_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Login_Fragment newInstance(String param1, String param2) {
        Login_Fragment fragment = new Login_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Book_Database database = Book_Database.getInstance(getActivity());
        EditText email = (EditText) view.findViewById(R.id.login_page_email);
        EditText pass = (EditText) view.findViewById(R.id.login_page_pass);
        CheckBox remember = (CheckBox) view.findViewById(R.id.rememberme);
        Button login = (Button) view.findViewById(R.id.reset_page_btn);
        Button forget = (Button) view.findViewById(R.id.forget_Btn);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email.getText().toString().equals(""))
                {

                    Cursor ccc = database.fetch_one_user(email.getText().toString());
                    if(ccc != null)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final TextView question = new TextView(getContext());
                        question.setText(ccc.getString(6));
                        question.setTextSize(17.5F);
                        question.setPadding(0,0,0,30);
                        final EditText answer = new EditText(getContext());
                        final LinearLayout layout = new LinearLayout(getContext());
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layout.setPadding(75,75,75,75);
                        layout.addView(question);
                        layout.addView(answer);
                        builder.setTitle("Answer the following question.")
                                .setView(layout)
                                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(answer.getText().toString().equals(ccc.getString(7)))
                                        {
                                            Intent intent = new Intent(getActivity(),Forget_Password.class);
                                            intent.putExtra("email",email.getText().toString());
                                            startActivity(intent);
                                            getActivity().finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(getContext(), "Invalid Answer :(", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }


                }
                else
                {
                    Toast.makeText(view.getContext(), "Enter Your Email First.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("")||pass.getText().toString().equals(""))
                {

                    Toast.makeText(getActivity(), "EMPTY FIELDS !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(database.check_login(email.getText().toString(),pass.getText().toString()))
                    {
                        if(remember.isChecked())
                        {
                            database.remove_local();
                            database.inser_local(1,1,email.getText().toString(),0);
                            Toast.makeText(getActivity(), "LOGIN SUCCESSFULLY !", Toast.LENGTH_SHORT).show();
                            Intent replace = new Intent(getActivity() , MainActivity.class);
                            startActivity(replace);
                            getActivity().finish();
                        }
                        else
                        {
                            database.remove_local();
                            database.inser_local(1,0,email.getText().toString(),0);
                            Toast.makeText(getActivity(), "LOGIN SUCCESSFULLY !", Toast.LENGTH_SHORT).show();
                            Intent replace = new Intent(getActivity() , MainActivity.class);
                            startActivity(replace);
                            getActivity().finish();
                        }


                    }
                    else
                    {
                        Toast.makeText(getActivity(), "INVALID LOGIN !", Toast.LENGTH_SHORT).show();
                        email.setText("");
                        pass.setText("");
                    }
                }
            }
        });
        return view;

    }
}