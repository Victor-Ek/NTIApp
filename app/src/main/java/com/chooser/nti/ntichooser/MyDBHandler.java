package com.chooser.nti.ntichooser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "products.db"; //kan vara fel, lägg till .sqlite
    public static final String TABLE_SNACKS = "snacks";
    public static final String TABLE_DRINKS = "drinks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAMN = "_namn";
    public static final String COLUMN_PRIS = "_pris";
    public static final String COLUMN_INFO = "_info";
    public static final String COLUMN_NYTTIGHET = "_nyttighet";
    private static final String TAG = "myMessage";
    private static String DB_PATH = "/data/data/com.chooser.nti.ntichooser/databases/";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //    String query = "CREATE TABLE " + TABLE_SNACKS + "(" +
        //            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        //            COLUMN_NAMN + " TEXT," +
        //            COLUMN_PRIS + " INTEGER," +
        //            COLUMN_INFO + " TEXT," +
        //            COLUMN_NYTTIGHET + " INTEGER," +
        //            ");";
        //    db.execSQL(query);

        //addLowHealthy();
        //addMediumHealthy();
        //addHighHealthy();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS" + TABLE_SNACKS);
        Log.i(TAG, "Beep Boop, Delete Database");
        File file = new File(DB_PATH, DATABASE_NAME);
        boolean delete = file.delete();
        Log.i(TAG, "Beep Boop, Database Deleted");
        try {
            createDataBase();
        }catch (IOException ie){
            Log.i(TAG, "Beep Boop, Failed to Create, maybe you should give up");
        }
    }

    public void addToHistorik(String productSnack, String productDrink, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("snackNamn", productSnack);
        values.put("drinkNamn", productDrink);
        values.put("datum", date);

        db.insert("Historik", null, values);
        //String sql =
        //        "INSERT INTO Historik (snackNamn, drinkNamn, datum) VALUES(" + productSnack + productDrink + date + ")" ;
        //Log.i(TAG, sql);
        //db.execSQL(sql);
        Log.i(TAG, "inserted");
    }

    //Delete from database
    public void deleteProduct(String product){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SNACKS,COLUMN_NAMN + "IS" + product, null);
    }

    //Print snacks table
    public List getSnacks(boolean mjölk, boolean nut, boolean gluten){
        String mjölkAllergi = "null)";
        String nutkAllergi = "null)";
        String glutenAllergi = "null)";
        List<String> dbArray = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        String whereQuery = "(SELECT SnackId FROM Snack_Allergier_Relation WHERE AllergiId IS ";
        String andQurey = " AND _id NOT IN ";
        if(mjölk){
            mjölkAllergi = "2)";
        }else if(nut){
            nutkAllergi = "1)";
        }else if(gluten){
            glutenAllergi = "3)";
        }
        String query = "SELECT * FROM " + TABLE_SNACKS + " WHERE _id NOT IN " + whereQuery + mjölkAllergi + andQurey + whereQuery + nutkAllergi + andQurey + whereQuery + glutenAllergi;

        //Cursor point to location in your results
        Cursor c = db.rawQuery(query,null);
        //move to the first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("_namn"))!= null){
                dbArray.add(c.getString(c.getColumnIndex("_namn")));
            }
            c.moveToNext();
        }
        db.close();
        c.close();
        Log.i(TAG, "I made it out");
        return dbArray;
    }

    //Print drinks table
    public List getDrinks(boolean mjölk, boolean nut, boolean gluten){
        List<String> dbArray = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DRINKS + " WHERE 1";

        //Cursor point to location in your results
        Cursor c = db.rawQuery(query,null);
        //move to the first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("_namn"))!= null){
                dbArray.add(c.getString(c.getColumnIndex("_namn")));
            }
            c.moveToNext();
        }
        db.close();
        c.close();
        return dbArray;
    }

    public int getPriceSnack(String snackName){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SNACKS + " WHERE _namn IS " + "'" + snackName + "'";
        int price = 0;
        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();
        price += (c.getInt(2));

        db.close();
        c.close();
        return price;
    }

    public int getPriceDrink(String drinkName){
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_DRINKS + " WHERE _namn IS " + "'" + drinkName + "'";
        int price = 0;
        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();
        price += (c.getInt(2));

        db.close();
        c.close();
        return price;
    }



    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
            Log.i(TAG, "Database already exist");
        }else{
            Log.i(TAG, "Database does not exist");
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                Log.i(TAG, "Error copying database");
                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            Log.i(TAG, checkDB.toString());
        }catch(SQLiteException e){

            Log.i(TAG, "Database does not exist yet");
            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        Log.i(TAG, myInput + "<- my input");

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        Log.i(TAG, outFileName + "<- out file name");

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        Log.i(TAG, "Open database");
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }



}
