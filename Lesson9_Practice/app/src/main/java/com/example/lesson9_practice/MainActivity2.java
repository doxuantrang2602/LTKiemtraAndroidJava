package com.example.lesson9_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText edt_id2, edt_hoten2, edt_namsinh2;
    Button btn_add, btn_back, btn_tinh;
    MyDBHelper dbHelper = new MyDBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String hoTen = edt_hoten2.getText().toString().trim();
//                int namSinh = Integer.parseInt(edt_namsinh2.getText().toString());
//                long resultInsert = dbHelper.insert(hoTen, namSinh);
//                if (resultInsert == -1){
//                    Toast.makeText(MainActivity2.this, "Error", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity2.this, "Inserted", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_tinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int a = intent.getIntExtra("a", -1);
                int b = intent.getIntExtra("b", -1);
                int c = a + b;
                intent.putExtra("tong", c);
                setResult(33, intent);
                finish();
            }
        });

    }
    private void AnhXa(){
        edt_id2 = this.findViewById(R.id.edt_id2);
        edt_hoten2 = this.findViewById(R.id.edt_hoten2);
        edt_namsinh2 = this.findViewById(R.id.edt_namSinh2);
        btn_add = this.findViewById(R.id.btn_add);
        btn_back = this.findViewById(R.id.btn_back);
        btn_tinh = this.findViewById(R.id.btn_Tinh);
    }
}