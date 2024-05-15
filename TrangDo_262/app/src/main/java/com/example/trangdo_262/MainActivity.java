package com.example.trangdo_262;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv_lstCar;
    List<Taxi_262> arrListTaxi = new ArrayList<>();
    SqliteDB_26 dbHelper = new SqliteDB_26(this);
    Adapter_262 adapter;
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
        dbHelper = new SqliteDB_26(MainActivity.this, "Taxi_262", null, 1);
        dbHelper.insert(new Taxi_262(1, "Số xe 1", 14.6f, 100, 5));
        dbHelper.insert(new Taxi_262(2, "Số xe 2", 15.4f, 90, 6));
        dbHelper.insert(new Taxi_262(3, "Số xe 3", 16.3f, 80, 7));
        dbHelper.insert(new Taxi_262(4, "Số xe 4", 17.2f, 110, 8));
        dbHelper.insert(new Taxi_262(5, "Số xe 5", 18.1f, 60, 9));
        dbHelper.insert(new Taxi_262(6, "Số xe 6", 19.8f, 115, 10));
        arrListTaxi.addAll(dbHelper.getAllCar());
        adapter.notifyDataSetChanged();
        lv_lstCar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        registerForContextMenu(lv_lstCar);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        if (item.getItemId() == R.id.action_edit){
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("maxe", arrListTaxi.get(pos).getMaXe());
            intent.putExtra("soxe", arrListTaxi.get(pos).getSoXe());
            intent.putExtra("quangduong", arrListTaxi.get(pos).getQuangDuong());
            intent.putExtra("dongia", arrListTaxi.get(pos).getDonGia());
            intent.putExtra("khuyenmai", arrListTaxi.get(pos).getKhuyenMai());
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_delete){
            return true;
        }
        return super.onContextItemSelected(item);
    }
    private void AnhXa(){
        adapter = new Adapter_262(this, R.layout.item, arrListTaxi);
        lv_lstCar = this.findViewById(R.id.lv_lstCar);
        lv_lstCar.setAdapter(adapter);
    }
}