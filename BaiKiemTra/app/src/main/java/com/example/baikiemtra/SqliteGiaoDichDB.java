package com.example.baikiemtra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqliteGiaoDichDB extends SQLiteOpenHelper {
    private static final String DBName = "myDB";
    private static final int VERSION = 1;
    private static String TABLE_NAME = "GiaoDich";
    private static String MA = "ma";
    private static String NOIDUNG = "noidung";
    private static String NGAY = "ngay";
    private static String LOAIGIAODICH = "loaigiaodich";
    private static String HOTEN = "hoten";
    private static String SOTIEN = "sotien";
    private SQLiteDatabase myDB;
    public SqliteGiaoDichDB(@Nullable Context context){
        super(context, DBName, null, VERSION);
    }
    public SqliteGiaoDichDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SqliteGiaoDichDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public static String getMA() {
        return MA;
    }

    public static String getNOIDUNG() {
        return NOIDUNG;
    }

    public static String getNGAY() {
        return NGAY;
    }

    public static String getLOAIGIAODICH() {
        return LOAIGIAODICH;
    }

    public static String getHOTEN() {
        return HOTEN;
    }

    public static String getSOTIEN() {
        return SOTIEN;
    }

    public SQLiteDatabase getMyDB() {
        return myDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+ TABLE_NAME+ "("
                + MA + " integer primary key autoincrement,"
                + NOIDUNG + " text not null,"
                + NGAY + " text, "
                + LOAIGIAODICH + " integer, " // True: tiền đến, false: tiền đi
                + HOTEN + " text,"
                + SOTIEN +  " integer)";
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
    public void insert(GiaoDich gd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOIDUNG, gd.getNoiDung());
        values.put(NGAY, gd.getNgay());
        values.put(LOAIGIAODICH, gd.isLoaiGiaoDich());
        values.put(HOTEN, gd.getHoTen());
        values.put(SOTIEN, gd.getSoTien());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<GiaoDich> getALLGiaoDich() {
        ArrayList<GiaoDich> lst = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " ORDER BY " + LOAIGIAODICH + " DESC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                GiaoDich ts = new GiaoDich(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3) == 1,
                        cursor.getString(4),
                        cursor.getInt(5)
                );
                lst.add(ts);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lst;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}