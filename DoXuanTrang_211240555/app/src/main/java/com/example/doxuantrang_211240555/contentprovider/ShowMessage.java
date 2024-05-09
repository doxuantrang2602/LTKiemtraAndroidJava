package com.example.doxuantrang_211240555.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.doxuantrang_211240555.MainActivity;
import com.example.doxuantrang_211240555.R;

import java.util.ArrayList;

public class ShowMessage extends AppCompatActivity {
    Button btn_back2;
    ListView lv_message;
    ArrayList<String> messageList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);
        AnhXa();
        btn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMessage.this, MainActivity.class);
                startActivity(intent);
            }
        });
        readMessage();
    }
    private void readMessage(){
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = getContentResolver().query(uri, null, null, null);
        while (cursor.moveToNext()){
            int index_phonenumber = cursor.getColumnIndex("address");
            int index_date = cursor.getColumnIndex("date");
            int index_body = cursor.getColumnIndex("body");
            String phoneNumber = cursor.getString(index_phonenumber);
            String date = cursor.getString(index_date);
            String body = cursor.getString(index_body);
            messageList.add(phoneNumber + "\n" + date + "\n"+body);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void AnhXa(){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageList);
        btn_back2 = this.findViewById(R.id.btn_back2);
        lv_message = this.findViewById(R.id.lv_message);
        lv_message.setAdapter(adapter);
    }
}