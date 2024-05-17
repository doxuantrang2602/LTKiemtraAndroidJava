package com.example.baikiemtra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText edt_ngayThang, edt_hoTen, edt_noiDung, edt_soTien;
    RadioButton rdb_denTu, rdb_diDen;
    Button btn_them2, btn_quayLai;
    SqliteGiaoDichDB dbHelper = new SqliteGiaoDichDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
        dbHelper = new SqliteGiaoDichDB(MainActivity2.this, "GiaoDich", null, 1);
        btn_them2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngay = edt_ngayThang.getText().toString().trim();
                String hoTen = edt_hoTen.getText().toString().trim();
                String noiDung = edt_noiDung.getText().toString().trim();
                int soTien = Integer.parseInt(edt_soTien.getText().toString().trim());
                if (rdb_denTu.isChecked()){
                    GiaoDich giaoDich = new GiaoDich(noiDung, ngay, true, hoTen, soTien);
                    dbHelper.insert(giaoDich);
                } else {
                    GiaoDich giaoDich = new GiaoDich(noiDung, ngay, false, hoTen, soTien);
                    dbHelper.insert(giaoDich);
                }
                Toast.makeText(MainActivity2.this, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_quayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AnhXa(){
        btn_them2 = this.findViewById(R.id.btn_them2);
        btn_quayLai = this.findViewById(R.id.btn_quayLai);
        edt_ngayThang = this.findViewById(R.id.edt_ngayThang);
        edt_hoTen = this.findViewById(R.id.edt_hoTen);
        edt_noiDung = this.findViewById(R.id.edt_noiDung);
        edt_soTien = this.findViewById(R.id.edt_soTien);
        rdb_denTu = this.findViewById(R.id.rdb_denTu);
        rdb_diDen = this.findViewById(R.id.rdb_diDen);
    }
}