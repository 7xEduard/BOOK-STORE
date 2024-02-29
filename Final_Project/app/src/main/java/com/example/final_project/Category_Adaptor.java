package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Vector;

public class Category_Adaptor extends ArrayAdapter {
    private Vector<Cart_data> my_cart;
    private Context context;
    Book_Database database;

    public Category_Adaptor(Context context, int resource, int textViewResourceId, List Items, Book_Database database) {
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
        view = inflater.inflate(R.layout.activity_category_list,parent,false);
        ImageView icon = view.findViewById(R.id.cat_img);
        TextView name = view.findViewById(R.id.cat_name);
        TextView desc = view.findViewById(R.id.cat_cat);




        icon.setImageResource(my_cart.get(position).getImg());
        name.setText(my_cart.get(position).getName());
        desc.setText(my_cart.get(position).getCategory());

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.anim_one);
        view.setAnimation(animation);
        return view;
    }

}
