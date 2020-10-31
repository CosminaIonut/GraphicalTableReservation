package com.example.restaurantreservation;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="RestaurantApp.db";
    public static final String TABLE_NAME_RESTAURANT = "restaurant";

    public static final String TABLE_NAME_MAP = "map";
    public static final String TABLE_NAME_DOOR = "door_positioning";
    public static final String TABLE_NAME_TABLE = "table_positioning";
    public static final String TABLE_NAME_WINDOW = "window_positioning";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);  //when construcor is called -> databese is created
//        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {  // create table
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_RESTAURANT+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, ADRESS TEXT )");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_MAP+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, PHOTOBG TEXT,  RID INTEGER, FOREIGN KEY (RID) REFERENCES " + TABLE_NAME_RESTAURANT  + "( ID) )");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_TABLE+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, X  FLOAT, Y FLOAT , MAPID INTEGER, FOREIGN KEY (MAPID) REFERENCES " + TABLE_NAME_MAP  + "( ID)  )");
       // sqLiteDatabase.execSQL("create table " + TABLE_NAME_DOOR+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT,X , ADRESS TEXT )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {   //delete table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RESTAURANT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MAP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TABLE);

        onCreate(sqLiteDatabase);

    }
    public void insertData(){    //insert in Table
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Insert  INTO "+ TABLE_NAME_RESTAURANT + " (NAME,ADRESS) Values('McTest', 'Str. Paris 23')");
        db.execSQL("Insert  INTO "+ TABLE_NAME_MAP + " (PHOTOBG,RID) Values('McTest.jpg', 1)");
        db.execSQL("Insert  INTO "+ TABLE_NAME_TABLE + " (X,Y, MAPID) Values(700,700,1)");
        db.execSQL("Insert  INTO "+ TABLE_NAME_TABLE + " (X,Y, MAPID) Values(300,450,1)");



    }
    public Cursor getAllData(){    //Get data from table
        //insertData();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result= db.rawQuery("select * from " + TABLE_NAME_TABLE, null );
        return result;

    }
}
