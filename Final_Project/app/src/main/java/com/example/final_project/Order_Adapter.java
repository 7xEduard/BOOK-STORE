package com.example.final_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Vector;

public class Order_Adapter extends ArrayAdapter {
    private Vector<Cart_data> my_cart;
    private Context context;
    Book_Database database;

    public Order_Adapter(Context context, int resource, int textViewResourceId, List Items, Book_Database database) {
        super(context, resource, textViewResourceId, Items);
        this.my_cart = (Vector<Cart_data>) Items;
        this.context = context;
        this.database = database;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.activity_orders_list,parent,false);
        ImageView icon = view.findViewById(R.id.ord_img);
        TextView name = view.findViewById(R.id.ord_name);
        TextView desc = view.findViewById(R.id.ord_cat);
        ImageButton del = view.findViewById(R.id.ord_del);
        ImageButton conf = view.findViewById(R.id.ord_acc);





        int iddd = my_cart.get(position).getId();
        icon.setImageResource(my_cart.get(position).getImg());
        name.setText(my_cart.get(position).getName());
        desc.setText(my_cart.get(position).getCategory());


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(position,view);
            }
        });
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(database.check_cart(iddd))
                {
                    Cursor cursor = database.fetch_flag();
                    //CheckBox cart_btn = (CheckBox) view.findViewById(R.id.add_to_cart);
                    boolean remove = database.remove_cart(cursor.getString(2),iddd);
                    if(remove)
                    {

                        boolean ch = database.updateOrder(cursor.getString(2),iddd);
                        if(ch)
                        {
                            boolean in = database.insert_to_history(cursor.getString(2),iddd);
                            if(in) {
                                boolean remove_2 = database.remove_order(cursor.getString(2),iddd);
                                if(remove_2) {
                                    Toast.makeText(getContext(), "ORDER CONFIRMED :)", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();

                                }
                                else
                                {
                                    Toast.makeText(getContext(), "ORDER NOT CONFIRMED.", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else
                            {
                                Toast.makeText(getContext(), "ORDER NOT CONFIRMED.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(), "ORDER NOT CONFIRMED.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "ERROR !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.anim_one);
        view.setAnimation(animation);
        return view;
    }
    private void showDialog(int position, View view) {
        // Use the Builder class for convenient dialog construction
        int iddd = my_cart.get(position).getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Delete")
                .setMessage("Are you sure you want to delete this book from user cart")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        // Dismiss the dialog

                        if(database.check_cart(iddd))
                        {
                            Cursor cursor = database.fetch_flag();
                            //CheckBox cart_btn = (CheckBox) view.findViewById(R.id.add_to_cart);
                            boolean remove = database.remove_cart(cursor.getString(2),iddd);
                            if(remove)
                            {
                                boolean remove_2 = database.remove_order(cursor.getString(2),iddd);
                                if(remove_2) {
                                    Toast.makeText(getContext(), "ORDER CANCELED. !", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "ERROR !", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getContext(), "ERROR !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        // Create the AlertDialog object and show it
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private Fragment replace_fragment(Fragment new_fragment , @NonNull MenuItem item)
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
        return new_fragment;
    }
}
