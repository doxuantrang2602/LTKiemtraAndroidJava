package com.example.lesson4test_listview_spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    Button btn_back;
    TextView tv_kqtQua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
        Intent intent1 = getIntent();
        /*Bundle bundle = intent1.getBundleExtra("mybundle");
        if (bundle != null){
            String ht = bundle.getString("mon");
            tv_kqtQua.setText(ht);
        }*/
        String ht = intent1.getStringExtra("mon");
        tv_kqtQua.setText(ht);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AnhXa(){
        btn_back = this.findViewById(R.id.btn_Back);
        tv_kqtQua = this.findViewById(R.id.tv_ketQua);
    }
}