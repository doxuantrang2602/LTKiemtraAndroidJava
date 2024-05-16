package com.example.singmysong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText edt_id, edt_name, edt_singer, edt_time;
    Button btn_Add, btn_Back;
    SqliteDBBaiHat dbHelper = new SqliteDBBaiHat(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
        dbHelper = new SqliteDBBaiHat(MainActivity2.this, "BaiHat", null, 1);
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(edt_id.getText().toString());
                String name = edt_name.getText().toString().trim();
                String singer = edt_singer.getText().toString().trim();
                float time = Float.parseFloat(edt_time.getText().toString().trim());
                BaiHat baiHat = new BaiHat(id, name, singer, time);
                dbHelper.insert(baiHat);
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AnhXa(){
        edt_id = this.findViewById(R.id.edt_id);
        edt_name = this.findViewById(R.id.edt_name);
        edt_singer = this.findViewById(R.id.edt_singer);
        edt_time = this.findViewById(R.id.edt_time);
        btn_Add = this.findViewById(R.id.btn_Add);
        btn_Back = this.findViewById(R.id.btn_Back);
    }
}