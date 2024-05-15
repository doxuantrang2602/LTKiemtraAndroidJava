package com.example.doxuantrang_211240555;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DoXuanTrang_Sqlite extends SQLiteOpenHelper {
    private static final String DBName = "myDB";
    private static final int VERSION = 1;
    private static String TABLE_NAME = "Person";
    private static String ID = "id";
    private static String NAME = "name";
    private static String PHONE = "phone";
    private SQLiteDatabase myDB;
    public DoXuanTrang_Sqlite(@Nullable Context context){
        super(context, DBName, null, VERSION);
    }
    public DoXuanTrang_Sqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DoXuanTrang_Sqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getID() {
        return ID;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String getPHONE() {
        return PHONE;
    }

    public SQLiteDatabase getMyDB() {
        return myDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+ TABLE_NAME+ "("
                + ID + " integer primary key autoincrement,"
                + NAME + " text not null,"
                + PHONE + " text not null )";
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
    public void insert(Contact_Trang person){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, person.getId());
        values.put(NAME, person.getName());
        values.put(PHONE, person.getPhone());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void delete(int id, Contact_Trang person){
        String where = "ID = " + id;
        myDB.delete(TABLE_NAME, where, null);
    }
    public void update(int id, Contact_Trang person){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, person.getName());
        values.put(PHONE, person.getPhone());
        String where = "ID = "+id;
        db.update(TABLE_NAME, values,where, null);
    }
    public Cursor displayAll(){
        String query = "select * from "+ TABLE_NAME;
        return myDB.rawQuery(query, null);
    }
    public ArrayList<Contact_Trang> getAllPerson(){
        ArrayList<Contact_Trang> lst = new ArrayList<>();
        String query = "select * from "+TABLE_NAME + " ORDER BY " + NAME + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Contact_Trang person = new Contact_Trang(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                lst.add(person);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lst;
    }
    public void searchByID(int id){
        String query = "select * from "+ TABLE_NAME + " where ID = "+id;
        myDB.rawQuery(query,null);
    }
    public Cursor searchByName(String name){
        String query = "select * from "+TABLE_NAME + " where NAME like ?";
        return myDB.rawQuery(query, new String[]{"%" + name + "%"});
    }
    public Cursor searchContactPhoneNumber(Context mContext, String phone) {
        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE ? OR " +
                ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + phone + "%", "%" + phone + "%"};
        Cursor cursor = mContext.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
