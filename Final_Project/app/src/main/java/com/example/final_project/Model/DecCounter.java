package com.example.final_project.Model;

import android.content.Context;

import com.example.final_project.Book_Database;

public class DecCounter implements MakeOrder{
    int cont;

    public DecCounter(int cont) {
        this.cont = cont;
    }

    @Override
    public int buildOrder(Context context, Book_Database database, int book_id) {
       if(cont>=1)
       {
           if (cont-1>=0){
               boolean dec=database.decCounter(book_id,cont);
               if(dec==true)
               {
                   return 1;
               } else
                   return 0;
           }
       }
        return 0;
    }
}
