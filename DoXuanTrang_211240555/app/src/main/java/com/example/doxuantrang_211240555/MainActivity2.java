package com.example.doxuantrang_211240555;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText edt_id, edt_name, edt_phone;
    Button btn_Add, btn_Back, btn_Update;
    DoXuanTrang_Sqlite dbHelper = new DoXuanTrang_Sqlite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
        dbHelper = new DoXuanTrang_Sqlite(MainActivity2.this, "Person", null, 1);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(edt_id.getText().toString());
                String name = edt_name.getText().toString().trim();
                String phone = edt_phone.getText().toString().trim();
                Contact_Trang person = new Contact_Trang(id, name, phone);
                dbHelper.insert(person);
                Toast.makeText(MainActivity2.this, "Thêm thành công !", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = getIntent();
        edt_id.setText(String.valueOf(intent.getIntExtra("id", 0)));
        edt_name.setText(intent.getStringExtra("name"));
        edt_phone.setText(intent.getStringExtra("phone"));
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(edt_id.getText().toString());
                String name = edt_name.getText().toString().trim();
                String phone = edt_phone.getText().toString().trim();
                Contact_Trang newPerson = new Contact_Trang(id, name, phone);
                dbHelper.update(id, newPerson);
                Toast.makeText(MainActivity2.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AnhXa(){
        edt_id = this.findViewById(R.id.edt_id);
        edt_name = this.findViewById(R.id.edt_name);
        edt_phone = this.findViewById(R.id.edt_phone);
        btn_Add = this.findViewById(R.id.btn_Add);
        btn_Back = this.findViewById(R.id.btn_Back);
        btn_Update = this.findViewById(R.id.btn_Update);
    }
}