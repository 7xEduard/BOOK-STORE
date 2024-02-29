package com.example.final_project;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.Model.FacadeOrder;

import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cart_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cart_Fragment extends Fragment {
    public  static boolean btn_flag = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Cart_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favorite_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Cart_Fragment newInstance(String param1, String param2) {
        Cart_Fragment fragment = new Cart_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        //View nview = inflater.inflate(R.layout.activity_cart_list, container, false);
        Book_Database database =  Book_Database.getInstance(getActivity());
        Cursor cursor5 = database.fetch_flag();

        Vector<Cart_data> items = new Vector<Cart_data>();
        Cursor cursor = database.fetch_all_cart(cursor5.getString(2));
        Cursor cursor1;
        double totalSum = 0;
        while(!cursor.isAfterLast())
        {
            cursor1 = database.fetch_one_book(cursor.getInt(1));
            items.add(new Cart_data(cursor1.getInt(0),cursor1.getString(1),cursor1.getString(2),cursor1.getInt(10),cursor.getInt(2)));
            totalSum += (cursor1.getDouble(12)*cursor.getInt(2));
            cursor.moveToNext();
        }
        cursor.moveToFirst();
        if(cursor5.getInt(3) == 0)
        {
            btn_flag = false;
        }
        else
        {
            btn_flag = true;
        }
        ListView my_list = (ListView) view.findViewById(R.id.cart_list);
        Cart_Adapter adapter = new Cart_Adapter(getActivity(),R.layout.activity_cart_list,R.id.cart_name,items,database);
        my_list.setAdapter(adapter);
        TextView tSum = (TextView) view.findViewById(R.id.totalSum);
        tSum.setText(String.valueOf(totalSum)+" $");

        Button history = (Button) view.findViewById(R.id.historyBtn);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), History_Page.class);
                startActivity(intent);
            }
        });
        Button order = (Button) view.findViewById(R.id.orderBtn);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                while(!cursor.isAfterLast())
                {
                    FacadeOrder order1=new FacadeOrder(getContext(),database,cursor.getInt(1));
                    int c =order1.get_counter();
                    order1.dec_counter(c);
                    //Cursor cc= database.get_counter(cursor.getInt(1));
                    //database.decCounter(cursor.getInt(1))
                    int c3=order1.add_order(cursor5.getString(2));
                    //boolean add = database.add_order(cursor5.getString(2),cursor.getInt(1),0);
                    if(c3==1)
                    {
                        Toast.makeText(getContext(), "Order has been send to admin to check.", Toast.LENGTH_SHORT).show();
                        btn_flag = true;
                        boolean chek = database.updateLocal(cursor5.getInt(0),1);
                        if(!chek)
                        {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();


                    }
                    else
                    {
                        Toast.makeText(getContext(), "Order faild", Toast.LENGTH_SHORT).show();
                    }
                    cursor.moveToNext();
                }



            }
        });

        my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getActivity(), Book_Templete.class);
            String data = adapterView.getItemAtPosition(i).toString();
            Cursor cursor2=database.fetchBooksByName(data);
            Cursor cursor3=database.fetch_writer_name(cursor2.getInt(0));
            Cursor cursor4=database.fetch_author_data(cursor3.getString(0));
            intent.putExtra("bookId",cursor2.getInt(0));
            intent.putExtra("bookName", cursor2.getString(1));
            intent.putExtra("bookCategory",cursor2.getString(2));
            intent.putExtra("publishDate",cursor2.getString(3));
            intent.putExtra("description",cursor2.getString(4));
            intent.putExtra("language",cursor2.getString(5));
            intent.putExtra("chapters",cursor2.getString(6));
            intent.putExtra("pages",cursor2.getString(7));
            intent.putExtra("parts",cursor2.getString(8));
            intent.putExtra("counter",cursor2.getString(9));
            intent.putExtra("bookLink",cursor2.getString(11));
            intent.putExtra("imageLink", cursor2.getInt(10));
            intent.putExtra("authorName",cursor4.getString(0));
            intent.putExtra("authorNationality",cursor4.getString(1));
            intent.putExtra("noOfBooks",cursor4.getString(2));
            intent.putExtra("price",cursor2.getString(12));
            intent.putExtra("edition",cursor2.getString(13));
            intent.putExtra("availability",cursor2.getString(14));
            intent.putExtra("popularity",cursor2.getString(15));
            startActivity(intent);
            }
        });
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_one);
        my_list.setAnimation(animation);
        return view;
    }
}