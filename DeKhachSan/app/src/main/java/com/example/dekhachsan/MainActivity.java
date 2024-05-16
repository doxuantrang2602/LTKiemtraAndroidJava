package com.example.dekhachsan;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv_lstBill;
    Adapter_262 adapter;
    List<HoaDon_26> arrListHoaDon = new ArrayList<>();
    EditText edt_search;
    Sqlite_262 dbHelper = new Sqlite_262(this);
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
        dbHelper = new Sqlite_262(MainActivity.this, "Taxi_262", null, 1);
        dbHelper.insert(new HoaDon_26(1, "Đỗ Xuân Tráng", 301, 100, 5));
        dbHelper.insert(new HoaDon_26(2, "Nguyễn Văn A", 303, 90, 2));
        dbHelper.insert(new HoaDon_26(3, "Nguyễn Văn B", 309, 120, 3));
        dbHelper.insert(new HoaDon_26(4, "Nguyễn Văn C", 302, 110, 6));
        dbHelper.insert(new HoaDon_26(5, "Nguyễn Văn D", 304, 130, 1));
        arrListHoaDon.addAll(dbHelper.getAllBill());
        adapter.notifyDataSetChanged();
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().trim();
                if(searchText.isEmpty()){
                    arrListHoaDon.clear();
                    arrListHoaDon.addAll(dbHelper.getAllBill());
                }else {
                    filterBill(Integer.parseInt(searchText));
                }
                adapter.notifyDataSetChanged();
            }
        });
        lv_lstBill.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int tongTien = arrListHoaDon.get(position).tongTien();
                int cnt = dbHelper.countBill(tongTien);
                Toast.makeText(MainActivity.this, "Đỗ Xuân Tráng "+cnt, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void filterBill(int tongTienNhap) {
        Cursor cursor = dbHelper.searchBillPrice(tongTienNhap);
        ArrayList<HoaDon_26> filterList = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                int ma = cursor.getInt(0);
                String hoTen = cursor.getString(1);
                int soPhong = cursor.getInt(2);
                int donGia = cursor.getInt(3);
                int soNgay = cursor.getInt(4);
                filterList.add(new HoaDon_26(ma, hoTen, soPhong, donGia, soNgay));
            }
            cursor.close();
        }
        arrListHoaDon.clear();
        arrListHoaDon.addAll(filterList);
        adapter.notifyDataSetChanged();
    }

    private void AnhXa(){
        adapter = new Adapter_262(this, android.R.layout.simple_list_item_1, arrListHoaDon);
        lv_lstBill = this.findViewById(R.id.lv_lstBill);
        lv_lstBill.setAdapter(adapter);
        edt_search = this.findViewById(R.id.edt_search);
    }
}