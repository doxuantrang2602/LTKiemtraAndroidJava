package com.example.singmysong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv_lstBaiHat;
    BaiHatAdapter adapter;
    List<BaiHat> arrListBaiHat = new ArrayList<>();
    FloatingActionButton btn_them;
    SqliteDBBaiHat dbHelper = new SqliteDBBaiHat(this);
    @Override
    protected void onStart() {
        super.onStart();
        dbHelper.OpenDB();
    }
    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.CloseDB();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        dbHelper = new SqliteDBBaiHat(MainActivity.this, "BaiHat", null, 1);
        dbHelper.insert(new BaiHat(1, "Bài hát 1", "Nguyễn Văn A", 3.12f));
        dbHelper.insert(new BaiHat(2, "Bài hát 2", "Nguyễn Văn B", 2.56f));
        dbHelper.insert(new BaiHat(3, "Bài hát 3", "Nguyễn Văn C", 5.16f));
        dbHelper.insert(new BaiHat(4, "Bài hát 4", "Nguyễn Văn D", 4.13f));
        dbHelper.insert(new BaiHat(5, "Bài hát 5", "ĐỖ Xuân Tráng", 3.18f));
        dbHelper.insert(new BaiHat(6, "Bài hát 6", "Nguyễn Văn E", 3.48f));
        arrListBaiHat.addAll(dbHelper.getAllBaiHat());
        adapter.notifyDataSetChanged();
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }
    private void AnhXa(){
        adapter = new BaiHatAdapter(this, android.R.layout.simple_list_item_1, arrListBaiHat);
        lv_lstBaiHat = this.findViewById(R.id.lv_lstBaiHat);
        lv_lstBaiHat.setAdapter(adapter);
        btn_them = this.findViewById(R.id.btn_them);
    }
}