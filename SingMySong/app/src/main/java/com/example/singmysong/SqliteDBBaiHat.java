package com.example.singmysong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqliteDBBaiHat extends SQLiteOpenHelper {
    private static final String DBName = "myDB";
    private static final int VERSION = 1;
    private static String TABLE_NAME = "BaiHat";
    private static String ID = "id";
    private static String NAME = "name";
    private static String SINGER = "singer";
    private static String TIME = "time";
    private SQLiteDatabase myDB;
    public SqliteDBBaiHat(@Nullable Context context){
        super(context, DBName, null, VERSION);
    }
    public SqliteDBBaiHat(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SqliteDBBaiHat(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public static String getID() {
        return ID;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String getSINGER() {
        return SINGER;
    }

    public static String getTIME() {
        return TIME;
    }

    public SQLiteDatabase getMyDB() {
        return myDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+ TABLE_NAME+ "("
                + ID + " integer primary key autoincrement,"
                + NAME + " text not null,"
                + SINGER + " text not null,"
                + TIME + " float )";
        db.execSQL(query);
    }
    public void OpenDB(){
        if(myDB == null || !myDB.isOpen()){
            myDB = getWritableDatabase();
        }
    }
    public void CloseDB(){
        if(myDB == null || myDB.isOpen()){
            myDB.close();
        }
    }
    public void insert(BaiHat baiHat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, baiHat.getId());
        values.put(NAME, baiHat.getName());
        values.put(SINGER, baiHat.getSinger());
        values.put(TIME, baiHat.getTime());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public ArrayList<BaiHat> getAllBaiHat(){
        ArrayList<BaiHat> lst = new ArrayList<>();
        String query = "select * from "+TABLE_NAME + " ORDER BY " + TIME + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                BaiHat baiHat = new BaiHat(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getFloat(3)
                );
                lst.add(baiHat);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lst;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}