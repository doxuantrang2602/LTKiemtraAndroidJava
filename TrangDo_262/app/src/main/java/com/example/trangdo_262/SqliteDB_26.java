package com.example.trangdo_262;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqliteDB_26 extends SQLiteOpenHelper {
    private static final String DBName = "myDB";
    private static final int VERSION = 1;
    private static String TABLE_NAME = "Taxi_262";
    private static String MAXE = "maxe";
    private static String SOXE = "soxe";
    private static String QUANGDUONG = "quangduong";
    private static String DONGIA = "dongia";
    private static String KHUYENMAI = "khuyenmai";
    private SQLiteDatabase myDB;
    public SqliteDB_26(@Nullable Context context){
        super(context, DBName, null, VERSION);
    }
    public SqliteDB_26(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SqliteDB_26(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getMAXE() {
        return MAXE;
    }

    public static String getSOXE() {
        return SOXE;
    }

    public static String getQUANGDUONG() {
        return QUANGDUONG;
    }

    public static String getDONGIA() {
        return DONGIA;
    }

    public static String getKHUYENMAI() {
        return KHUYENMAI;
    }

    public SQLiteDatabase getMyDB() {
        return myDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+ TABLE_NAME+ "("
                + MAXE + " integer primary key autoincrement,"
                + SOXE + " text not null,"
                + QUANGDUONG + " float, "
                + DONGIA + " integer, "
                + KHUYENMAI + " integer )";
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
    public void insert(Taxi_262 taxi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MAXE, taxi.getMaXe());
        values.put(SOXE, taxi.getSoXe());
        values.put(QUANGDUONG, taxi.getQuangDuong());
        values.put(DONGIA, taxi.getDonGia());
        values.put(KHUYENMAI, taxi.getKhuyenMai());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void update(int maXe, Taxi_262 taxi){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SOXE, taxi.getSoXe());
        values.put(QUANGDUONG, taxi.getQuangDuong());
        values.put(DONGIA, taxi.getDonGia());
        values.put(KHUYENMAI, taxi.getKhuyenMai());
        String where = "MAXE = "+maXe;
        db.update(TABLE_NAME, values,where, null);
    }
    public ArrayList<Taxi_262> getAllCar(){
        ArrayList<Taxi_262> lst = new ArrayList<>();
        String query = "select * from "+TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Taxi_262 taxi = new Taxi_262(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getFloat(2),
                        cursor.getInt(3),
                        cursor.getInt(4)
                );
                lst.add(taxi);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lst;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
