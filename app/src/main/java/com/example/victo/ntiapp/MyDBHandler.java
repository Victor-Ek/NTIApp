package com.example.victo.ntiapp;

import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "products.db";
    public static final String TABLE_SNACKS = "snacks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAMN = "_namn";
    public static final String COLUMN_PRIS = "_pris";
    public static final String COLUMN_INFO = "_info";
    public static final String COLUMN_NYTTIGHET = "_nyttighet";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_SNACKS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAMN + " TEXT," +
                COLUMN_PRIS + " INTEGER," +
                COLUMN_INFO + " TEXT," +
                COLUMN_NYTTIGHET + " INTEGER," +
                ");";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_SNACKS);
        onCreate(db);
    }

    //Add a new row to the database
    public void addProduct(Snacks product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMN, product.get_namn());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SNACKS, null, values);
        db.close();
    }

    //Delete from database
    public void deleteProduct(String product){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SNACKS,COLUMN_NAMN + "IS" + product, null);
    }

    //Print out db as string
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SNACKS + "WHERE 1";

        //Cursor point to location in your results
        Cursor c =db.rawQuery(query,null);
        //move to the first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("_namn"))!= null){
                dbString += c.getString(c.getColumnIndex("_namn"));
                dbString += "\n";
            }
        }
        db.close();
        return  dbString;
    }

}
