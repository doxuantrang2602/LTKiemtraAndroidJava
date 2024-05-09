package com.example.lesson9_practice;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edt_id, edt_hoten, edt_namsinh;
    Button btn_them, btn_xoa, btn_sua, btn_tim, btn_load, btn_gui;
    FloatingActionButton btn_them2;
    TextView tv_ketQua;
    ListView lv_sinhVien;
    MyDBHelper dbHelper = new MyDBHelper(this);
    ArrayList<SinhVien> arrayListSinhVien;
    SinhVienAdapter adapter;
    private ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == 33){
                        Intent intent = result.getData();
                        int t = intent.getIntExtra("tong", -1);
                        tv_ketQua.setText(String.valueOf(t));
                    }
                }
            });
    int val;

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper.OpenDb();
    }
    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.CloseDb();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = edt_id.getText().toString().trim();
                String hoTen = edt_hoten.getText().toString().trim();
                String namSinhStr = edt_namsinh.getText().toString().trim();

                int id = Integer.parseInt(idStr);
                Integer namSinh = Integer.parseInt(namSinhStr);

                long resultInsert = dbHelper.insert(id, hoTen, namSinh);
                if (resultInsert == -1){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                    loadTable();
                }
            }
        });

        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTable();
            }
        });
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idCanXoa = Integer.parseInt(edt_id.getText().toString().trim());
                long resultDelete = dbHelper.delete(idCanXoa);
                if (resultDelete == -1){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    loadTable();
                }
            }
        });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idCanSua = Integer.parseInt(edt_id.getText().toString().trim());
                String hoTen = edt_hoten.getText().toString().trim();
                int namSinh = Integer.parseInt(edt_namsinh.getText().toString().trim());
                long resultUpdate = dbHelper.update(idCanSua, hoTen, namSinh);
                if(resultUpdate == -1){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    loadTable();
                }
            }
        });
        btn_tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int idCanTim = Integer.parseInt(edt_id.getText().toString().trim());
                arrayListSinhVien.clear();
                String tenCanTim = edt_hoten.getText().toString().trim();
                StringBuffer buffer = new StringBuffer();
                Cursor cursor = dbHelper.searchByHoTen(tenCanTim);
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                    int id = cursor.getInt(cursor.getColumnIndex(MyDBHelper.getID()));
                    String ten = cursor.getString(cursor.getColumnIndex(MyDBHelper.getNAME()));
                    int namSinh = cursor.getInt(cursor.getColumnIndex(MyDBHelper.getBIRTHYEAR()));
                    SinhVien sinhVien = new SinhVien(id, ten, namSinh);
                    arrayListSinhVien.add(sinhVien);
                    adapter = new SinhVienAdapter(MainActivity.this, R.layout.sinhvien_item, arrayListSinhVien);
                    lv_sinhVien.setAdapter(adapter);

                    buffer.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.getID())));
                    buffer.append(" - ");
                    buffer.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.getNAME())));
                    buffer.append(" - ");
                    buffer.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.getBIRTHYEAR())));
                    buffer.append("\n");
                }
                tv_ketQua.setText(buffer.toString());

            }
        });
        btn_them2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        btn_gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solve();
            }
        });
        lv_sinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idSV = arrayListSinhVien.get(position).getId();
                String hoTen = arrayListSinhVien.get(position).getName().toString();
                int namSinh = arrayListSinhVien.get(position).getBirthyear();
                edt_id.setText(String.valueOf(idSV));
                edt_hoten.setText(hoTen);
                edt_namsinh.setText(String.valueOf(namSinh));
                Toast.makeText(MainActivity.this, ""+arrayListSinhVien.get(position).getName(), Toast.LENGTH_SHORT).show();
                val = position;
                xacnhanxoa();
            }
        });
    }

    private void xacnhanxoa() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Xác nhận xóa");
        alertDialog.setMessage("Bạn có chắc chắn muốn xóa không?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int idSV = arrayListSinhVien.get(val).getId();
                long resultDelete = dbHelper.delete(idSV);
                if (resultDelete != -1) {
                    Toast.makeText(MainActivity.this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show();
                    arrayListSinhVien.remove(val);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi khi xóa sinh viên", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void solve() {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        int a = Integer.parseInt(edt_hoten.getText().toString());
        int b = Integer.parseInt(edt_namsinh.getText().toString());
        intent.putExtra("a", a);
        intent.putExtra("b", b);
        activityResultLauncher.launch(intent);
    }

    private void loadTable() {
        arrayListSinhVien.clear();
        StringBuffer buffer = new StringBuffer();
        Cursor cursor = dbHelper.DisplayAll();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(MyDBHelper.getID()));
                String ten = cursor.getString(cursor.getColumnIndex(MyDBHelper.getNAME()));
                int namSinh = cursor.getInt(cursor.getColumnIndex(MyDBHelper.getBIRTHYEAR()));
                SinhVien sinhVien = new SinhVien(id, ten, namSinh);
                arrayListSinhVien.add(sinhVien);

                buffer.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.getID())));
                buffer.append(" - ");
                buffer.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.getNAME())));
                buffer.append(" - ");
                buffer.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.getBIRTHYEAR())));
                buffer.append("\n");
            }
            cursor.close();
        }
        adapter = new SinhVienAdapter(MainActivity.this, R.layout.sinhvien_item, arrayListSinhVien);
        lv_sinhVien.setAdapter(adapter);
        tv_ketQua.setText(buffer);
    }
    private void AnhXa(){
        edt_id = this.findViewById(R.id.edt_id);
        edt_hoten = this.findViewById(R.id.edt_hoten);
        edt_namsinh = this.findViewById(R.id.edt_namsinh);
        btn_them = this.findViewById(R.id.btn_them);
        btn_xoa = this.findViewById(R.id.btn_xoa);
        btn_sua = this.findViewById(R.id.btn_sua);
        btn_tim = this.findViewById(R.id.btn_tim);
        btn_load = this.findViewById(R.id.btn_loadTable);
        btn_them2 = this.findViewById(R.id.btn_them2);
        btn_gui = this.findViewById(R.id.btn_gui);
        tv_ketQua = this.findViewById(R.id.tv_ketQua);
        lv_sinhVien = this.findViewById(R.id.lv_sinhVien);
        arrayListSinhVien = new ArrayList<SinhVien>();
    }
}


