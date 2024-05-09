package com.example.lesson9_practice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DBName = "myDb";
    private static int VERSION = 1;
    private static String TABLE_NAME = "SinhVien";
    private static String ID = "id";
    private static String NAME = "name";
    private static String BIRTHYEAR = "birthyear";
    private SQLiteDatabase myDB;

    public MyDBHelper(@Nullable Context context) {
        super(context, DBName, null, VERSION);
    }

    public static String getID() {
        return ID;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String getBIRTHYEAR() {
        return BIRTHYEAR;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable ="create table "+TABLE_NAME + "("+ID+" integer primary key, " +
                NAME+" text not null, " +
                BIRTHYEAR + " integer not null )";
        db.execSQL(queryTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void OpenDb(){
        myDB =getWritableDatabase();
    }
    public void CloseDb(){
        if(myDB != null && myDB.isOpen()){
            myDB.close();
        }
    }
    //insert
    public long insert(int id, String name, int birthyear){
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(NAME, name);
        values.put(BIRTHYEAR, birthyear);
        return myDB.insert(TABLE_NAME,null, values);
    }
    // display
    public Cursor DisplayAll(){
        String query = "select * from "+TABLE_NAME;
        return myDB.rawQuery(query, null);
    }
    //update
    public long update(int id, String name, int birthyear){
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(BIRTHYEAR, birthyear);
        String where = "ID = "+id;
        return myDB.update(TABLE_NAME,values,where,null);
    }
    //delete
    public long delete(int id){
        String where = "ID = "+id;
        return myDB.delete(TABLE_NAME, where, null);
    }
    public Cursor searchByID(int id){
        String query = "Select * from "+TABLE_NAME + " where "+ID+" = "+id;
        return myDB.rawQuery(query, null);
    }
    public Cursor searchByHoTen(String name){
        String queryName = "Select * from " + TABLE_NAME + " where " + NAME + " = '" + name + "'";
        return myDB.rawQuery(queryName, null);
    }
}
