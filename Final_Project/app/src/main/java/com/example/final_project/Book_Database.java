package com.example.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.final_project.Model.TopBooks;

import java.util.ArrayList;
import java.util.List;

public class Book_Database extends SQLiteOpenHelper {
    //Database Name
    private static String database_name = "Apk_database";
    //Database Object
    SQLiteDatabase n_database;
    //Database Constructor
    private Book_Database(Context context) {
        super(context, database_name , null , 26);
    }
    //Singleton Pattern
    private static Book_Database instance=null;
    public static synchronized Book_Database getInstance(Context context) {
        if (instance == null) {
            instance = new Book_Database(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE COUNTER (id integer primary key autoincrement,i integer)");

        sqLiteDatabase.execSQL("CREATE TABLE AUTHOR (author_name text primary key,nationality text,no_of_books integer)");

        sqLiteDatabase.execSQL("CREATE TABLE USER (email text primary key,password text , userName text , address text , phone text , birthdate text , question text , answer text)");

        sqLiteDatabase.execSQL("CREATE TABLE BOOKS (book_id integer primary key autoincrement,name text,category text,publish_date text," +
                "description text,language text,chapters integer," +
                "pages integer,parts integer,counter integer,image_link integer,book_link text,price REAL,edition text,availability INTEGER,popularity text)");

        sqLiteDatabase.execSQL("CREATE TABLE CART_OF_BOOK (email text,book_id integer,quantity integer,primary key(email,book_id)," +
                "foreign key(email) REFERENCES USER (email) ON DELETE CASCADE," +
                "foreign key(book_id) REFERENCES BOOKS (book_id) ON DELETE CASCADE)");

        ///////////////////////////////////
        sqLiteDatabase.execSQL("CREATE TABLE HISTORY_OF_BOOK (email text,book_id integer,primary key(email,book_id)," +
                "foreign key(email) REFERENCES USER (email) ON DELETE CASCADE," +
                "foreign key(book_id) REFERENCES BOOKS (book_id) ON DELETE CASCADE)");
        //////////////////////////////////////////

        sqLiteDatabase.execSQL("CREATE TABLE WRITE (author_name text,book_id integer,primary key(author_name,book_id)," +
                "foreign key(author_name) REFERENCES AUTHOR (author_name) ON DELETE CASCADE," +
                "foreign key(book_id) REFERENCES BOOKS (book_id) ON DELETE CASCADE)");

        sqLiteDatabase.execSQL("CREATE TABLE LOCAL_USER (animi integer primary key,flag integer,name text,colorFlag integer)");

        sqLiteDatabase.execSQL("CREATE TABLE BOOK_SELLS (id integer primary key,count integer)");

        sqLiteDatabase.execSQL("CREATE TABLE USER_REVIEW (email text,book_id integer,review text,primary key(email,book_id)," +
                "foreign key(email) REFERENCES USER (email) ON DELETE CASCADE," +
                "foreign key(book_id) REFERENCES BOOKS (book_id) ON DELETE CASCADE)");

        sqLiteDatabase.execSQL("CREATE TABLE USER_RATE (email text,book_id integer,rate integer,primary key(email,book_id)," +
                "foreign key(email) REFERENCES USER (email) ON DELETE CASCADE," +
                "foreign key(book_id) REFERENCES BOOKS (book_id) ON DELETE CASCADE)");


        sqLiteDatabase.execSQL("CREATE TABLE USER_ORDER (email text,book_id integer,flag integer,primary key(email,book_id)," +
                "foreign key(email) REFERENCES USER (email) ON DELETE CASCADE," +
                "foreign key(book_id) REFERENCES BOOKS (book_id) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists AUTHOR");
        sqLiteDatabase.execSQL("drop table if exists USER");
        sqLiteDatabase.execSQL("drop table if exists BOOKS");
        sqLiteDatabase.execSQL("drop table if exists CART_OF_BOOK");
        sqLiteDatabase.execSQL("drop table if exists WRITE");
        sqLiteDatabase.execSQL("drop table if exists LOCAL_USER");
        sqLiteDatabase.execSQL("drop table if exists USER_REVIEW");
        sqLiteDatabase.execSQL("drop table if exists USER_ORDER");
        onCreate(sqLiteDatabase);
    }
    public boolean add_review(String email , int book_id,String review)
    {
        ContentValues rev = new ContentValues();
        rev.put("email",email);
        rev.put("book_id",book_id);
        rev.put("review",review);
        n_database = this.getWritableDatabase();
        long add = n_database.insert("USER_REVIEW",null,rev);
        if(add == -1)
        {
            n_database.close();
            return false;
        }
        else
        {
            n_database.close();
            return true;
        }
    }
    public boolean add_rate(String email , int book_id,int rate)
    {
        ContentValues rev = new ContentValues();
        rev.put("email",email);
        rev.put("book_id",book_id);
        rev.put("rate",rate);
        n_database = this.getWritableDatabase();
        long add = n_database.insert("USER_RATE",null,rev);
        if(add == -1)
        {
            n_database.close();
            return false;
        }
        else
        {
            n_database.close();
            return true;
        }
    }
    public boolean add_order(String email , int book_id,int flag)
    {
        ContentValues rev = new ContentValues();
        rev.put("email",email);
        rev.put("book_id",book_id);
        rev.put("flag",flag);
        n_database = this.getWritableDatabase();
        long add = n_database.insert("USER_ORDER",null,rev);
        if(add == -1)
        {
            n_database.close();
            return false;
        }
        else
        {
            n_database.close();
            return true;
        }
    }

    public Cursor fetch_one_comment(int id)
    {
        String[] book = {String.valueOf(id)};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from USER_REVIEW where book_id = ?" , book);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;

    }
    public Cursor fetch_one_rate(int id)
    {
        String[] book = {String.valueOf(id)};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from USER_RATE where book_id = ?" , book);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;

    }
    public Cursor fetch_orders_for_one_user(String email)
    {
        String[] order = {email};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from USER_ORDER where email = ?" , order);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;

    }
    public Cursor fetch_all_orders()
    {
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from USER_ORDER",null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;
    }


    public void inser_local(int animi,int flag,String name,int colorFlag)
    {
        ContentValues local_data = new ContentValues();
        local_data.put("animi",animi);
        local_data.put("flag",flag);
        local_data.put("name",name);
        local_data.put("colorFlag",colorFlag);
        n_database = this.getWritableDatabase();
        long check = n_database.insert("LOCAL_USER" , null , local_data);
        n_database.close();
    }

    public boolean updateSells(int book_id , int rate)
    {
        n_database = this.getWritableDatabase();
        ContentValues local_data = new ContentValues();
        local_data.put("count",rate);
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(book_id)};
        int count = n_database.update("BOOK_SELLS", local_data, selection, selectionArgs);
        if (count>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public Cursor get_sells(int book_id)
    {
        String[] rate = {String.valueOf(book_id)};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from BOOK_SELLS where id = ?" , rate);

        if(cursor.getCount() >0)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;
    }
    public void inser_sells(int id,int count)
    {
        ContentValues local_data = new ContentValues();
        local_data.put("id",id);
        local_data.put("count",count);


        n_database = this.getWritableDatabase();
        long check = n_database.insert("BOOK_SELLS" , null , local_data);
        n_database.close();
    }
    public boolean updateLocal(int animi , int newFlag)
    {
        n_database = this.getWritableDatabase();
        ContentValues local_data = new ContentValues();
        local_data.put("colorFlag",newFlag);
        String selection = "animi=?";
        String[] selectionArgs = {String.valueOf(animi)};
        int count = n_database.update("LOCAL_USER", local_data, selection, selectionArgs);
        if (count>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    public boolean updateOrder(String email , int book_id)
    {
        n_database = this.getWritableDatabase();
        ContentValues local_data = new ContentValues();
        local_data.put("flag",1);
        String selection = "email=? and book_id = ?";
        String[] selectionArgs = {email,String.valueOf(book_id)};
        int count = n_database.update("USER_ORDER", local_data, selection, selectionArgs);
        if (count>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    public boolean updateRate(String email , int book_id , int rate)
    {
        n_database = this.getWritableDatabase();
        ContentValues local_data = new ContentValues();
        local_data.put("rate",rate);
        String selection = "email=? and book_id = ?";
        String[] selectionArgs = {email,String.valueOf(book_id)};
        int count = n_database.update("USER_RATE", local_data, selection, selectionArgs);
        if (count>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    public Cursor fetch_flag()
    {
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from LOCAL_USER" , null);
        cursor.moveToFirst();
        n_database.close();
        return cursor;
    }
    public void remove_local()
    {
        n_database=getWritableDatabase();
        n_database.execSQL("DELETE FROM LOCAL_USER");
        n_database.close();
    }
    public boolean check_userName(String userName)
    {
        String[] user_array = {userName};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from USER where userName = ?" , user_array);
        if(cursor.getCount() > 0)
        {
            n_database.close();
            return true;
        }
        else
        {
            n_database.close();
            return false;
        }
    }
    public boolean check_user_email(String email)
    {
        String[] email_array = {email};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from USER where email = ?" , email_array);
        if(cursor.getCount() > 0)
        {
            n_database.close();
            return true;
        }
        else
        {
            n_database.close();
            return false;
        }
    }
    public Cursor fetch_all_user()
    {
        String getAll="SELECT * FROM USER";
        n_database=getReadableDatabase();
        Cursor cursor=n_database.rawQuery(getAll,null);
        cursor.moveToFirst();
        n_database.close();
        return cursor;
    }
    public boolean check_login(String email , String password)
    {
        String[] email_array = {email , password};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from USER where email = ? and password = ?" , email_array);
        if(cursor.getCount() > 0)
        {
            n_database.close();
            return true;
        }
        else
        {
            n_database.close();
            return false;
        }
    }
    public Cursor get_rate(String email , int book_id)
    {
        String[] rate = {email , String.valueOf(book_id)};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from USER_RATE where email = ? and book_id = ?" , rate);

        if(cursor.getCount() >0)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;
    }
    public boolean insert_user_data(String email , String password , String userName , String address, String phone, String birthdate , String question , String answer)
    {
        ContentValues user_data = new ContentValues();
        user_data.put("email",email);
        user_data.put("password",password);
        user_data.put("userName",userName);
        user_data.put("address",address);
        user_data.put("phone",phone);
        user_data.put("birthdate",birthdate);
        user_data.put("question",question);
        user_data.put("answer",answer);
        n_database = this.getWritableDatabase();
        long check = n_database.insert("USER" , null , user_data);
        if(check == -1)
        {
            n_database.close();
            return false;
        }
        else
        {
            n_database.close();
            return true;
        }
    }
    public Cursor fetch_one_user(String email)
    {
        String[] user = {email};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from USER where email = ?" , user);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;
    }

    public boolean updatePass(String email , String pass)
    {
        n_database = this.getWritableDatabase();
        ContentValues local_data = new ContentValues();
        local_data.put("password",pass);
        String selection = "email=?";
        String[] selectionArgs = {email};
        int count = n_database.update("USER", local_data, selection, selectionArgs);
        if (count>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    public void remove_user(String email)
    {
        String[] check = {email};
        n_database = this.getWritableDatabase();
        n_database.delete("USER","email = ?",check);
        n_database.close();
    }
    public Cursor fetch_one_book(int id)
    {
        String[] book = {String.valueOf(id)};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from BOOKS where book_id = ?" , book);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;
    }
    public Cursor fetch_author_data(String name)
    {
        String[] data = {name};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from AUTHOR where author_name = ?" , data);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;
    }
    public Cursor fetch_writer_name(int id)
    {
        String[] data = {String.valueOf(id)};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from WRITE where book_id = ?" , data);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;
    }
    public boolean insert_to_cart(String email , int book_id , int quantity)
    {
        ContentValues cart = new ContentValues();
        cart.put("email",email);
        cart.put("book_id",book_id);
        cart.put("quantity",quantity);
        n_database = this.getWritableDatabase();
        long add = n_database.insert("CART_OF_BOOK",null,cart);
        if(add == -1)
        {
            n_database.close();
            return false;
        }
        else
        {
            n_database.close();
            return true;
        }
    }
    public boolean insert_to_history(String email , int book_id)
    {
        ContentValues cart = new ContentValues();
        cart.put("email",email);
        cart.put("book_id",book_id);
        n_database = this.getWritableDatabase();
        long add = n_database.insert("HISTORY_OF_BOOK",null,cart);
        if(add == -1)
        {
            n_database.close();
            return false;
        }
        else
        {
            n_database.close();
            return true;
        }
    }
    public boolean check_cart(int book_id)
    {
        String[] cart = {String.valueOf(book_id)};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from CART_OF_BOOK where book_id = ?" , cart);
        if(cursor.getCount() > 0)
        {
            n_database.close();
            return true;
        }
        else
        {
            n_database.close();
            return false;
        }
    }
    public boolean remove_cart(String email , int book_id)
    {
        String[] remove_item = {email,String.valueOf(book_id)};

        n_database = this.getWritableDatabase();
        n_database.execSQL("delete from CART_OF_BOOK where email = ? and book_id = ?",remove_item);
        boolean check_cart = check_cart(book_id);
        if(check_cart)
        {
            n_database.close();
            return false;
        }
        else
        {
            n_database.close();
            return true;
        }
    }
    public boolean remove_order(String email , int book_id)
    {
        String[] remove_item = {email,String.valueOf(book_id)};

        n_database = this.getWritableDatabase();
        n_database.execSQL("delete from USER_ORDER where email = ? and book_id = ?",remove_item);
        boolean check_cart = check_cart(book_id);
        if(check_cart)
        {
            n_database.close();
            return false;
        }
        else
        {
            n_database.close();
            return true;
        }
    }
    public Cursor fetch_all_cart(String email)
    {
        String[] mail = {email};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from CART_OF_BOOK where email = ?",mail);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;
    }
    public Cursor fetch_all_history(String email)
    {
        String[] mail = {email};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from HISTORY_OF_BOOK where email = ?",mail);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        n_database.close();
        return cursor;
    }
    public void insertBooks(int bookId, String bookName, String category, String publishDate, String desc,String language, int chapters, int pages, int parts, int counter, int imageLink, String bookLink, double price, String edition, int availability, String popularity){
        n_database=getWritableDatabase();
        n_database.execSQL("insert into BOOKS VALUES ("+String.valueOf(bookId)+",'"+bookName+"','"+category+"','"+publishDate+"','"+desc+"','"+language+"',"+String.valueOf(chapters)+","+String.valueOf(pages)+","+String.valueOf(parts)+","+String.valueOf(counter)+","+String.valueOf(imageLink)+",'"+bookLink+"','"+price+"','"+edition+"','"+availability+"','"+popularity+"')");
        n_database.close();
    }

    public void insertAuthor(String name,String nationality,int no_of_books){
        n_database=getWritableDatabase();
        n_database.execSQL("insert into AUTHOR VALUES ('"+name+"','"+nationality+"',"+String.valueOf(no_of_books)+");");
        n_database.close();
    }
    public void insertWrite(String author_name,int book_id){
        n_database=getWritableDatabase();
        n_database.execSQL("insert into WRITE values('"+author_name+"',"+String.valueOf(book_id)+")");
        n_database.close();
    }
    public Cursor fetchBooks(){
        String getAll="SELECT * FROM BOOKS";
        n_database=getReadableDatabase();
        Cursor cursor=n_database.rawQuery(getAll,null);
        cursor.moveToFirst();
        n_database.close();
        return cursor;
    }
    public List<TopBooks> fetchOneBook(String category){
        List<TopBooks> l = new ArrayList<TopBooks>();
        String getAll;
        if(category!="all")
            getAll="SELECT * FROM BOOKS where category = '"+category+"'";
        else
            getAll="SELECT * FROM BOOKS";
        n_database=getReadableDatabase();
        Cursor cursor=n_database.rawQuery(getAll,null);
        if(cursor!=null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TopBooks obj=new TopBooks();
                obj.setBookName(cursor.getString(1));
                obj.setAuthorName(fetchOneWrite(Integer.valueOf(cursor.getString(0))).getString(0));
                obj.setImage(Integer.parseInt(cursor.getString(10)));
                l.add(obj);
                cursor.moveToNext();
            }
        }
        return l;
    }
    public Cursor fetchOneAuthor(String author_name){
        String getAll="SELECT * FROM AUTHOR where author_name = '"+author_name+"';";
        n_database=getReadableDatabase();
        Cursor cursor=n_database.rawQuery(getAll,null);
        cursor.moveToFirst();
        return cursor;
    }
    public Cursor fetchOneWrite(int book_id){
        String getAll="SELECT * FROM WRITE where book_id = "+String.valueOf(book_id)+";";
        n_database=getReadableDatabase();
        Cursor cursor=n_database.rawQuery(getAll,null);
        cursor.moveToFirst();
        n_database.close();
        return cursor;
    }
    public void delete(){
        n_database=getWritableDatabase();
        n_database.execSQL("DELETE FROM BOOKS;");
        n_database.execSQL("DELETE FROM AUTHOR ;");
        n_database.execSQL("DELETE FROM USER;");
        n_database.execSQL("DELETE FROM CART_OF_BOOK;");
        n_database.execSQL("DELETE FROM WRITE;");
        n_database.execSQL("DELETE FROM LOCAL_USER");
        n_database.execSQL("DELETE FROM USER_REVIEW");
        n_database.execSQL("DELETE FROM USER_ORDER");
        n_database.close();
    }
    public Cursor fetchBooksByName(String name){
        String getAll="SELECT * FROM BOOKS where name = '"+name+"'";
        n_database=getReadableDatabase();
        Cursor cursor=n_database.rawQuery(getAll,null);
        cursor.moveToFirst();
        return cursor;
    }

    /////////////////////////////////////////////
    public Cursor get_counter(int book_id)
    {
        String[] book = {String.valueOf(book_id)};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from BOOKS where book_id = ?" , book);
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            if (cursor.getInt(9)>1)
            {
                n_database.close();
                return cursor;
            }
            return null;
        }
        else
        {
            n_database.close();
            return null;
        }
    }
    /////////////////////////////////////////////
    public boolean decCounter(int book_id ,int counter)
    {
        n_database = this.getWritableDatabase();
        ContentValues local_data = new ContentValues();
        local_data.put("counter",counter-1);
        String selection = "book_id=?";
        String[] selectionArgs = {String.valueOf(book_id)};
        int count = n_database.update("BOOKS", local_data, selection, selectionArgs);
        if (count>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }


    public void remove_category(String cat)
    {
        String[] remove_item = {cat};

        n_database = this.getWritableDatabase();
        n_database.execSQL("delete from BOOKS where category = ?",remove_item);
        n_database.close();

    }



    public boolean add_counter_flag(int id , int counter)
    {
        ContentValues rev = new ContentValues();
        rev.put("id",id);
        rev.put("i",counter);
        n_database = this.getWritableDatabase();
        long add = n_database.insert("COUNTER",null,rev);
        if(add == -1)
        {
            n_database.close();
            return false;
        }
        else
        {
            n_database.close();
            return true;
        }
    }
    public Cursor fetch_counter_flag()
    {
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from COUNTER" , null);
        cursor.moveToFirst();
        n_database.close();
        return cursor;
    }
    public boolean update_counter_flag()
    {
        Cursor cursor = fetch_counter_flag();
        n_database = this.getWritableDatabase();
        ContentValues local_data = new ContentValues();
        local_data.put("i",cursor.getInt(1)+1);
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(0)};
        int count = n_database.update("COUNTER", local_data, selection, selectionArgs);
        if (count>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    public Cursor fetch_one_category(String cat)
    {
        String[] user_array = {cat};
        n_database = this.getReadableDatabase();
        Cursor cursor = n_database.rawQuery("select * from BOOKS where category = ?" , user_array);
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            n_database.close();
            return cursor;
        }
        else
        {
            n_database.close();
            return null;
        }
    }
    public void remove_one_book(int book_id)
    {
        String[] remove_item = {String.valueOf(book_id)};

        n_database = this.getWritableDatabase();
        n_database.execSQL("delete from BOOKS where book_id = ?",remove_item);

    }

    public boolean editBook(int bookId, String bookName, String category, String publishDate, String desc,String language, int chapters, int pages, int parts, int counter, int imageLink, String bookLink, double price, String edition, int availability, String popularity)
    {
        n_database = this.getWritableDatabase();
        ContentValues local_data = new ContentValues();
        local_data.put("name",bookName);
        local_data.put("category",category);
        local_data.put("publish_date",publishDate);
        local_data.put("description",desc);
        local_data.put("language",language);
        local_data.put("chapters",chapters);
        local_data.put("pages",pages);
        local_data.put("parts",parts);
        local_data.put("counter",counter);
        local_data.put("image_link",imageLink);
        local_data.put("book_link",bookLink);
        local_data.put("price",price);
        local_data.put("edition",edition);
        local_data.put("availability",availability);
        local_data.put("popularity",popularity);
        String selection = "book_id=?";
        String[] selectionArgs = {String.valueOf(bookId)};
        int count = n_database.update("BOOKS", local_data, selection, selectionArgs);
        if (count>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    ////////////////////////////////////////////


    public void set_all()
    {
        //HORROR
        insertBooks(1,"Beach Town","HORROR","2020","Harry and his family live a modest suburban life in Beach Town, on an island Southwest of the UK. He is dissatisfied with his marriage when he agrees to take his best friend Sheila to a job interview.","ENGLISH",32,195,1,5,R.drawable.horror_1,"https://drive.google.com/file/d/1CBUBEnTt6aUAHSvoInN7RE7bSStt3fiV/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(2,"The Apples of Idunn","HORROR","2017","In the cold winters of an ice age, Odin was born to be a mere jarl. But Fate has staked a claim upon him. When his brother falls under a ghostly curse, a goddess offers Odin a chance to save him. If Odin can make himself king—and promise an unspecified favor—she will make him and his family immortal.","ENGLISH",7,328,1,10,R.drawable.horror_2,"https://drive.google.com/file/d/1VJckwG2GboX4oz0NbZ8PV-LLNi7cEC-N/view?usp=sharing",51.2,"6",1,"Popular");
        insertBooks(3,"Immortals Requiem","HORROR","2018","Bestseller & OnlineBookClub.orgs Book of the Year 2018 There are beings that live a shadows breadth from our reality... They are the dreams and nightmares of humanity, the ancient seeds of fairy-tale and superstition. These are the Immortals, creatures of magic that should live forever... and they are fading.","ENGLISH",18,469,1,9,R.drawable.horror_3,"https://drive.google.com/file/d/1PcLSio3m21EGKrN_C1z-TT7y8stanQU5/view?usp=sharing",18.0,"2",0,"Popular");
        insertBooks(4,"Gallowglass","HORROR","2017","After Karina and her brother, Chriss, lives fall apart in separate yet equally spectacular ways, they leave New York behind and head to the UK. Karina buries herself in research for her doctoral thesis, all the while studiously not thinking about the man who broke her heart, while Chris—whod been a best-selling author before his ex-fiancée sued him for plagiarism—drinks his way across the British Isles.","ENGLISH",3,217,1,6,R.drawable.horror_4,"https://drive.google.com/file/d/1XXTG51B_vMbuwEVQbwuQ-YTkYpR1d72Q/view?usp=sharing",24.5,"1",1,"Not Popular");
        insertAuthor("Thomas Maxwell-Harrison","American",1);
        insertAuthor("Matt Larkin","American",2);
        insertAuthor("Vincent Bobbe","American",1);
        insertAuthor("Jennifer Allis Provost","American",1);
        insertWrite("Thomas Maxwell-Harrison",1);
        insertWrite("Matt Larkin",2);
        insertWrite("Vincent Bobbe",3);
        insertWrite("Jennifer Allis Provost",4);
        //FANTASY
        insertBooks(5,"A Shifters Curse","FANTASY","2018","But infiltrating them proves more challenging than she expects, especially when her new roommates turn out to be supernaturals with their own powers and secrets. She cant tell if the centuries-old, nosey vampire and fashion-sensitive witch are working with her or against her.","ENGLISH",47,336,1,8,R.drawable.fantasy_1,"https://drive.google.com/file/d/1hTkV24pPa1PP7lLpaSgnpd8Rz4GgYxqx/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(6,"Ice Crown","FANTASY","2019","Talise can manipulate the elements with ease. Water, air, earth, and fire all bend to her will. As a citizen of the Storm—a crime-laden land where death is the only constant—her only chance for a better life is to become Master Shaper.","ENGLISH",15,78,1,3,R.drawable.fantasy_2,"https://drive.google.com/file/d/1vctQ5aOJwVuMSJeuf2_DfJ6gcihXFJ__/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(7,"Marked","FANTASY","2019","Kara thought that her lack of wings was a problem in the world of Valkyries–until she stumbled across a dragons nest. She soon learned that wherever you found a dragons nest, a mother dragon was always nearby. No matter how much combat training she had, she could still become lunch.","ENGLISH",3,24,1,6,R.drawable.fantasy_3,"https://drive.google.com/file/d/1qHFXXMUeYBhIqx0HE6JtwHZ-92g9rNCG/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(8,"The Red Diamond","FANTASY","2020","Iman has respected and obeyed her grandfather Charles and his teachings all through her young life and for this reason Charles trusts her to always be responsible and keep the wizard code of conduct close to her heart, especially the ruling whereby a wizard can never fall in love with an ordinary human. He even gifts her a magical red diamond to keep all humans away from her.","ENGLISH",7,39,1,4,R.drawable.fantasy_4,"https://drive.google.com/file/d/1BQQrXV10O1EWrLRj3eEPLgtGo3MsZ0dr/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(9,"The Ultimate Sacrifice","FANTASY","2011","Kassia is a 16-year-old girl just trying to have a normal life. This is somewhat difficult because she has the ability to inflict pain on others with her mind, which is why she was sent to a special institute for kids with various gifts. It is here Kassia met her best friend Mira.","ENGLISH",19,174,1,6,R.drawable.fantasy_5,"https://drive.google.com/file/d/1tRDei15UpxQPfP0EjKpzRsldUwF7fcxx/view?usp=sharing",20.5,"4",1,"Popular");
        insertAuthor("Raven Steele","American",2);
        insertAuthor("Kay L. Moody","American",1);
        insertAuthor("Katrina Cope","American",1);
        insertAuthor("Emily Flowers","American",1);
        insertAuthor("Talia Jager","American",3);
        insertWrite("Raven Steele",5);
        insertWrite("Kay L. Moody",6);
        insertWrite("Katrina Cope",7);
        insertWrite("Emily Flowers",8);
        insertWrite("Talia Jager",9);
        //MYSTERY
        insertBooks(10,"Chloe - Lost Girl","MYSTERY","2019","Detective Inspector Carl Sant and his team get on the case. But what links the disappearance of a university student, the death of an off-duty police sergeant, and a professor reluctant to help them solve the case?","ENGLISH",14,185,1,8,R.drawable.mystery_1,"https://drive.google.com/file/d/1_LKDNKtaZIgW3eLfxPr3aWgYm-yPLRQP/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(11,"Dream Doctor","MYSTERY","2013","Between adjusting to life as a newlywed and trying to survive the first month of medical school, Sara Alderson has a lot on her plate.&nbsp; She definitely doesnt need to start visiting other peoples dreams again.&nbsp; Unfortunately for her, its happening anyway.","ENGLISH",16,248,1,9,R.drawable.mystery_2,"https://drive.google.com/file/d/1lCT5_0FIgBpKwdkqKJ7wXOV7V1gIx3S3/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(12,"Fields Guide","MYSTERY","2018","Poppy Fields, Hollywood IT girl extraordinaire, agreed to a week at the newest, most luxurious resort in Cabo. After all, whats better than the beach when a girl is feeling blue? When Poppy is abducted, she will need all her smarts, all her charm, and a killer Chihuahua, to save herself in this new series from the USA TODAY bestselling author of The Country Club Murders.","ENGLISH",26,224,1,7,R.drawable.mystery_3,"https://drive.google.com/file/d/1HHpoIqhN6a8lfxw0sI7MN4v1wRHiqKmo/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(13,"Induction","MYSTERY","2019","Sidonie & Sinclair Boudreau were the offspring of a witch and a shifter. Such pairings usually resulted in death. Sid & Sin had not only survived, but thrived, and managed to sidestep the family legacy of supernatural policing.","ENGLISH",21,183,1,11,R.drawable.mystery_4,"https://drive.google.com/file/d/1vEhicCRnS_PgfGvzgqLev8zW8A448pYr/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(14,"Brain Storm","MYSTERY","2015","Taylor Morrison - a private investigator who is suddenly gifted with a psychic ability.","ENGLISH",46,340,1,0,R.drawable.mystery_5,"https://drive.google.com/file/d/1Tm9N6Wq3FfBo8cfCUJHU5myQu6nyyHA_/view?usp=sharing",20.5,"4",1,"Popular");
        insertAuthor("Dan Laughey","American",1);
        insertAuthor("J.J. DiBenedetto","American",3);
        insertAuthor("Julie Mulhern","American",1);
        insertAuthor("T.K. Eldridge","American",5);
        insertAuthor("Cat Gilbert","American",1);
        insertWrite("Dan Laughey",10);
        insertWrite("J.J. DiBenedetto",11);
        insertWrite("Julie Mulhern",12);
        insertWrite("T.K. Eldridge",13);
        insertWrite("Cat Gilbert",14);
        //SCIENCE FICTION
        insertBooks(15,"The Boy","SCIENCE FICTION","2015","It is 2055. Floods have devastated London. Confined to his house with only his cat, his mothers domestic robot, and his holographic dragons for company, Mathew Erlang is desperate for a distraction. He finds it in his peculiar and reclusive neighbour, August Lestrange.","ENGLISH",5,193,1,12,R.drawable.science_1,"https://drive.google.com/file/d/1_mA4mre8WVFoL_AD6SW80KQzXwqvAFNy/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(16,"Open Minds","SCIENCE FICTION","2013","Open Minds is the first novel in the Mindjack Saga, a young adult science fiction series.","ENGLISH",35,212,1,11,R.drawable.science_2,"https://drive.google.com/file/d/12hWXotE-AFy0e0VjFN-VVCUi-5UlhS4f/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(17,"Blindsight","SCIENCE FICTION","2006","Who do you send to meet the alien when the alien doesnt want to meet?","ENGLISH",16,362,1,8,R.drawable.science_3,"https://drive.google.com/file/d/1ZcN_FaS_fuJBRHwcIiFg_yyBwKUbN3PO/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(18,"The Defenders","SCIENCE FICTION","1953","No weapon has ever been frightful enough to put a stop to war--perhaps because we never before had any that thought for themselves!","ENGLISH",2,37,1,7,R.drawable.science_4,"https://drive.google.com/file/d/1DGfBNrd27d_yGDkq_fHED2eowQYNmhsJ/view?usp=sharing",20.5,"4",1,"Popular");
        insertAuthor("Jule Owen","American",1);
        insertAuthor("Susan Kaye Quinn","American",1);
        insertAuthor("Peter Watts","American",1);
        insertAuthor("Philip K. Dick","American",1);
        insertWrite("Jule Owen",15);
        insertWrite("Susan Kaye Quinn",16);
        insertWrite("Peter Watts",17);
        insertWrite("Philip K. Dick",18);
        //ROMANCE
        insertBooks(19,"The Unveiling","ROMANCE","2013","12th century England: Two men vie for the throne: King Stephen the usurper and young Duke Henry the rightful heir. Amid civil and private wars, alliances are forged, loyalties are betrayed, families are divided, and marriages are made.","ENGLISH",22,252,1,6,R.drawable.romance_1,"https://drive.google.com/file/d/1lzhPFCEePs81zKatnS4vpslDn4u7AdjF/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(20,"Ascension","ROMANCE","424","Running out of money after her fathers death, Sabrina Strong is attacked on her way to a job interview at Tremayne Towers. Narrowly saved by a strangely familiar vampire, she soon finds herself tangled in the murder of Vampire Master Bjorn Tremaynes mate, Letitia.","ENGLISH",28,291,1,9,R.drawable.romance_2,"https://drive.google.com/file/d/1H5rSO-bmlptSz7VXHe60pidqjQ8dI-U4/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(21,"Elizabeth","ROMANCE","2020","A warlock from the underworld in the guise of a human. An honorable pirate with his heart set on revenge.","ENGLISH",26,159,1,8,R.drawable.romance_3,"https://drive.google.com/file/d/1pV2hm1N6rd84jfBJbi2GOpD6t4YK5GuY/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(22,"Chosen","ROMANCE","2015","When a fatal fever nearly wipes out the entire worlds population, the survivors of what became known as the Dying believe the worst is in the past. Little do they know…","ENGLISH",18,230,1,9,R.drawable.romance_4,"https://drive.google.com/file/d/1cF9BXw_r2kBmaTRWg4l2x_rghXP5bym-/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(23,"Remembrance","ROMANCE","2019","I was three when I watched my mother die. Now Im back in Muckle Cove digging into her murder. They say coming home is never easy, but what Im finding goes so much deeper than anyone could have imagined.","ENGLISH",24,189,1,14,R.drawable.romance_5,"https://drive.google.com/file/d/1U-Mwh3jJx2kMunpcSxHuMBT1GszKTMnW/view?usp=sharing",20.5,"4",1,"Popular");
        insertAuthor("Tamara Leigh","American",1);
        insertAuthor("Lorelei Bell","American",1);
        insertAuthor("Angelique S. Anderson","American",2);
        insertAuthor("Christine Pope","American",2);
        insertWrite("Tamara Leigh",19);
        insertWrite("Lorelei Bell",20);
        insertWrite("Angelique S. Anderson",21);
        insertWrite("Christine Pope",22);
        insertWrite("T.K. Eldridge",23);
        ////////////////////////////////////////////
        ////////////////////////////////////////////////////
        //////////////////////////////////
        //OTHERS
        insertBooks(24,"The Unveiling","OTHERS","2013","12th century England: Two men vie for the throne: King Stephen the usurper and young Duke Henry the rightful heir. Amid civil and private wars, alliances are forged, loyalties are betrayed, families are divided, and marriages are made.","ENGLISH",22,252,1,6,R.drawable.romance_1,"https://drive.google.com/file/d/1lzhPFCEePs81zKatnS4vpslDn4u7AdjF/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(25,"Ascension","OTHERS","424","Running out of money after her fathers death, Sabrina Strong is attacked on her way to a job interview at Tremayne Towers. Narrowly saved by a strangely familiar vampire, she soon finds herself tangled in the murder of Vampire Master Bjorn Tremaynes mate, Letitia.","ENGLISH",28,291,1,9,R.drawable.romance_2,"https://drive.google.com/file/d/1H5rSO-bmlptSz7VXHe60pidqjQ8dI-U4/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(26,"Elizabeth","OTHERS","2020","A warlock from the underworld in the guise of a human. An honorable pirate with his heart set on revenge.","ENGLISH",26,159,1,8,R.drawable.romance_3,"https://drive.google.com/file/d/1pV2hm1N6rd84jfBJbi2GOpD6t4YK5GuY/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(27,"Chosen","OTHERS","2015","When a fatal fever nearly wipes out the entire worlds population, the survivors of what became known as the Dying believe the worst is in the past. Little do they know…","ENGLISH",18,230,1,9,R.drawable.romance_4,"https://drive.google.com/file/d/1cF9BXw_r2kBmaTRWg4l2x_rghXP5bym-/view?usp=sharing",20.5,"4",1,"Popular");
        insertBooks(28,"Remembrance","OTHERS","2019","I was three when I watched my mother die. Now Im back in Muckle Cove digging into her murder. They say coming home is never easy, but what Im finding goes so much deeper than anyone could have imagined.","ENGLISH",24,189,1,14,R.drawable.romance_5,"https://drive.google.com/file/d/1U-Mwh3jJx2kMunpcSxHuMBT1GszKTMnW/view?usp=sharing",20.5,"4",1,"Popular");
        /*insertAuthor("Tamara Leigh","American",1);
        insertAuthor("Lorelei Bell","American",1);
        insertAuthor("Angelique S. Anderson","American",2);
        insertAuthor("Christine Pope","American",2);*/
        insertWrite("Tamara Leigh",24);
        insertWrite("Lorelei Bell",25);
        insertWrite("Angelique S. Anderson",26);
        insertWrite("Christine Pope",27);
        insertWrite("T.K. Eldridge",28);
        ///////////////////////////////
        add_counter_flag(0,29);
    }

}
