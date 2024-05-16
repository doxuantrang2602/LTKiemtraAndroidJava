package com.example.dekhachsan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Sqlite_262 extends SQLiteOpenHelper {
    private static final String DBName = "myDB";
    private static final int VERSION = 1;
    private static String TABLE_NAME = "HoaDon_26";
    private static String MA = "ma";
    private static String HOTEN = "hoten";
    private static String SOPHONG = "sophong";
    private static String DONGIA = "dongia";
    private static String SONGAY = "songay";
    private SQLiteDatabase myDB;
    public Sqlite_262(@Nullable Context context){
        super(context, DBName, null, VERSION);
    }
    public Sqlite_262(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Sqlite_262(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getMA() {
        return MA;
    }

    public static String getHOTEN() {
        return HOTEN;
    }

    public static String getSOPHONG() {
        return SOPHONG;
    }

    public static String getDONGIA() {
        return DONGIA;
    }

    public static String getSONGAY() {
        return SONGAY;
    }

    public SQLiteDatabase getMyDB() {
        return myDB;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+ TABLE_NAME+ "("
                + MA + " integer primary key autoincrement,"
                + HOTEN + " text not null,"
                + SOPHONG + " integer, "
                + DONGIA + " integer, "
                + SONGAY + " integer)";
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
    public void insert(HoaDon_26 hoaDon){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MA, hoaDon.getMa());
        values.put(HOTEN, hoaDon.getHoTen());
        values.put(SOPHONG, hoaDon.getSoPhong());
        values.put(DONGIA, hoaDon.getDonGia());
        values.put(SONGAY, hoaDon.getSoNgay());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public ArrayList<HoaDon_26> getAllBill(){
        ArrayList<HoaDon_26> lst = new ArrayList<>();
        String query = "select * from "+TABLE_NAME + " ORDER BY " + SOPHONG + " DESC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                HoaDon_26 hoaDon = new HoaDon_26(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4)
                );
                lst.add(hoaDon);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lst;
    }
    public Cursor searchBillPrice(int tongTien){
        String query = "select * from "+ TABLE_NAME + " where DONGIA*SONGAY >= "+tongTien;
        return myDB.rawQuery(query,null);
    }
    public int countBill(int tongTien) {
        String query = "select count(*) from " + TABLE_NAME + " where DONGIA * SONGAY > " + tongTien;
        Cursor cursor = myDB.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } else {
            cursor.close();
            return 0;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}