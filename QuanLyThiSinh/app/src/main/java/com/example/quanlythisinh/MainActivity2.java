package com.example.quanlythisinh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText edt_soBaoDanh, edt_hoTen, edt_diemToan, edt_diemLy, edt_diemHoa;
    Button btn_them2, btn_Sua, btn_quayLai;
    SqliteThiSinhDB dbHelper = new SqliteThiSinhDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
        dbHelper = new SqliteThiSinhDB(MainActivity2.this, "ThiSinh", null, 1);
        Intent intent = getIntent();
        edt_soBaoDanh.setText(intent.getStringExtra("sobaodanh"));
        edt_hoTen.setText(intent.getStringExtra("hoten"));
        edt_diemToan.setText(String.valueOf(intent.getFloatExtra("toan", 0f)));
        edt_diemLy.setText(String.valueOf(intent.getFloatExtra("ly", 0f)));
        edt_diemHoa.setText(String.valueOf(intent.getFloatExtra("hoa", 0f)));
        btn_them2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soBaoDanh = edt_soBaoDanh.getText().toString().trim();
                String hoTen = edt_hoTen.getText().toString().trim();
                float toan = Float.parseFloat(edt_diemToan.getText().toString().trim());
                float ly = Float.parseFloat(edt_diemLy.getText().toString().trim());
                float hoa = Float.parseFloat(edt_diemHoa.getText().toString().trim());
                ThiSinh ts = new ThiSinh(soBaoDanh, hoTen, toan, ly, hoa);
                dbHelper.insert(ts);
                Toast.makeText(MainActivity2.this, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soBaoDanh = edt_soBaoDanh.getText().toString().trim();
                String hoTen = edt_hoTen.getText().toString().trim();
                float toan = Float.parseFloat(edt_diemToan.getText().toString().trim());
                float ly = Float.parseFloat(edt_diemLy.getText().toString().trim());
                float hoa = Float.parseFloat(edt_diemHoa.getText().toString().trim());
                ThiSinh ts = new ThiSinh(soBaoDanh, hoTen, toan, ly, hoa);
                dbHelper.update(soBaoDanh, ts);
                Toast.makeText(MainActivity2.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
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
        edt_soBaoDanh = this.findViewById(R.id.edt_soBaoDanh);
        edt_hoTen = this.findViewById(R.id.edt_hoTen);
        edt_diemToan = this.findViewById(R.id.edt_diemToan);
        edt_diemLy = this.findViewById(R.id.edt_diemLy);
        edt_diemHoa = this.findViewById(R.id.edt_diemHoa);
        btn_them2 = this.findViewById(R.id.btn_them2);
        btn_Sua = this.findViewById(R.id.btn_Sua);
        btn_quayLai = this.findViewById(R.id.btn_quayLai);
    }
}