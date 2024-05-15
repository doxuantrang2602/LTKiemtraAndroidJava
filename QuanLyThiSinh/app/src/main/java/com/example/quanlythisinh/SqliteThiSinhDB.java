package com.example.quanlythisinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqliteThiSinhDB extends SQLiteOpenHelper {
    private static final String DBName = "myDB";
    private static final int VERSION = 1;
    private static String TABLE_NAME = "ThiSinh";
    private static String SOBAODANH = "sobaodanh";
    private static String HOTEN = "hoten";
    private static String TOAN = "toan";
    private static String LY = "ly";
    private static String HOA = "hoa";
    private SQLiteDatabase myDB;
    public SqliteThiSinhDB(@Nullable Context context){
        super(context, DBName, null, VERSION);
    }
    public SqliteThiSinhDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SqliteThiSinhDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public static String getSOBAODANH() {
        return SOBAODANH;
    }

    public static String getHOTEN() {
        return HOTEN;
    }

    public static String getTOAN() {
        return TOAN;
    }

    public static String getLY() {
        return LY;
    }

    public static String getHOA() {
        return HOA;
    }

    public SQLiteDatabase getMyDB() {
        return myDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+ TABLE_NAME+ "("
                + SOBAODANH + " text primary key,"
                + HOTEN + " text not null,"
                + TOAN + " float, "
                + LY + " float, "
                + HOA + " float )";
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
    public void insert(ThiSinh ts){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SOBAODANH, ts.getSoBaoDanh());
        values.put(HOTEN, ts.getHoTen());
        values.put(TOAN, ts.getToan());
        values.put(LY, ts.getLy());
        values.put(HOA, ts.getHoa());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void delete(String soBaoDanh){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "SOBAODANH = '"+soBaoDanh+"'";
        db.delete(TABLE_NAME, where, null);
        db.close();
    }
    public void update(String soBaoDanh, ThiSinh ts){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HOTEN, ts.getHoTen());
        values.put(TOAN, ts.getToan());
        values.put(LY, ts.getLy());
        values.put(HOA, ts.getHoa());
        String where = "SOBAODANH = '"+soBaoDanh+"' " ;
        db.update(TABLE_NAME, values,where, null);
    }
    public ArrayList<ThiSinh> getAllThiSinh() {
        ArrayList<ThiSinh> lst = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " ORDER BY " + HOTEN + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ThiSinh ts = new ThiSinh(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getFloat(2),
                        cursor.getFloat(3),
                        cursor.getFloat(4)
                );
                lst.add(ts);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lst;
    }
    public void searchByID(String soBaoDanh){
        String query = "select * from "+ TABLE_NAME + " where SOBAODANH = '"+soBaoDanh+"'";
        myDB.rawQuery(query,null);
    }
    public Cursor searchByName(String hoTen){
        String query = "select * from "+TABLE_NAME + " where HOTEN like ?";
        return myDB.rawQuery(query, new String[]{"%" + hoTen + "%"});
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}