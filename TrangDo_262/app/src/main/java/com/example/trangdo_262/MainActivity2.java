package com.example.trangdo_262;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText edt_soXe, edt_quangDuong, edt_donGia, edt_khuyenMai;
    Button btn_Sua, btn_quayLai;
    SqliteDB_26 dbHelper = new SqliteDB_26(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
        dbHelper = new SqliteDB_26(MainActivity2.this, "Taxi_262", null, 1);
        Intent intent = getIntent();
        edt_soXe.setText(intent.getStringExtra("soxe"));
        edt_quangDuong.setText(String.valueOf(intent.getFloatExtra("quangduong", 0)));
        edt_donGia.setText(String.valueOf(intent.getIntExtra("dongia", 0)));
        edt_khuyenMai.setText(String.valueOf(intent.getIntExtra("khuyenmai", 0)));
        btn_Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maXe = intent.getIntExtra("maxe", 0);
                String soXe = edt_soXe.getText().toString().trim();
                float quangDuong = Float.parseFloat(edt_quangDuong.getText().toString().trim());
                int donGia = Integer.parseInt(edt_donGia.getText().toString().trim());
                int khuyenMai = Integer.parseInt(edt_khuyenMai.getText().toString().trim());
                Taxi_262 newTaxi = new Taxi_262(maXe, soXe, quangDuong, donGia, khuyenMai);
                dbHelper.update(maXe, newTaxi);
                Toast.makeText(MainActivity2.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_quayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
    private void AnhXa(){
        edt_soXe = this.findViewById(R.id.edt_soXe);
        edt_quangDuong = this.findViewById(R.id.edt_quangDuong);
        edt_donGia = this.findViewById(R.id.edt_donGia);
        edt_khuyenMai = this.findViewById(R.id.edt_khuyenMai);
        btn_Sua = this.findViewById(R.id.btn_Sua);
        btn_quayLai = this.findViewById(R.id.btn_quayLai);
    }
}