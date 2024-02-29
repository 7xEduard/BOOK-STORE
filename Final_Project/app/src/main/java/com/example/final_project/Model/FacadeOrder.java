package com.example.final_project.Model;

import android.content.Context;

import com.example.final_project.Book_Database;

public class FacadeOrder {
    private MakeOrder getCount;
    private MakeOrder decCount;
    private MakeOrder addOrder;

    Context context;
    Book_Database database;
    int book_id;


    public FacadeOrder(Context context,Book_Database database, int book_id) {
        this.context=context;
        this.database=database;
        this.book_id=book_id;
        getCount=new GetCounter();
//        decCount=new DecCounter(get_counter());
//        addOrder=new AddOrder(email);
    }

    public int get_counter(){
        return getCount.buildOrder(context,database,book_id);
    }
    public int dec_counter(int c){
        decCount=new DecCounter(c);
        return decCount.buildOrder(context,database,book_id);
    }
    public int add_order(String e)
    {
        addOrder=new AddOrder(e);
        return addOrder.buildOrder(context,database,book_id);
    }
}
