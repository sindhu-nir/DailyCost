package com.example.root.dailycost;

/**
 * Created by root on 4/20/17.
 */

/**
 * Created by root on 4/19/17.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.Timestamp;
import java.util.Date;

import static android.R.attr.name;

/**
 * Created by User on 2/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "cost_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "benson";
    private static final String COL3 = "gold_leaf";
    private static final String COL4 = "tea";
    private static final String COL5 = "other";
    private static final String COL6 = "total";
    private static final String COL7 = "date";




    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT, " +
                COL3 +" TEXT, " +
                COL4 +" TEXT,"+
                COL5 +" TEXT,"+
                COL6 +" TEXT,"+
                COL7 +" Timestamp)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String benson,String goldLeaf,String tea,String other,String total,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, benson);
        contentValues.put(COL3, goldLeaf);
        contentValues.put(COL4, tea);
        contentValues.put(COL5, other);
        contentValues.put(COL6, total);
        contentValues.put(COL7, date);


        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(String from, String to){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME+" Where date>='"+from+"' and date<='"+to+"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL3 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public void updateName(String newProduct,String newItem,String newQuantity, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = '" + newProduct + "',"+COL3+ "= '"+newItem+"', "+COL4+" = '"+newQuantity+ "' WHERE " + COL1 + " = " + id+"" ;

        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newItem);
        db.execSQL(query);


    }


    public void deleteRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = " + id + "";

        db.execSQL(query);
    }

    public void deleteAll(){


        SQLiteDatabase db = this.getWritableDatabase(); //get database
        db.execSQL("DELETE FROM cost_table"); //delete all rows in a table
        db.execSQL("DELETE FROM sqlite_sequence");
        db.close();

        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
    }

}














