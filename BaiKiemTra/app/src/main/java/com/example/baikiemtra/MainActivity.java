package com.example.baikiemtra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tv_soDu;
    ListView lv_lstGiaoDich;
    ImageButton btn_them;
    GiaoDichAdapter adapter;
    List<GiaoDich> arrListGiaoDich = new ArrayList<>();
    SqliteGiaoDichDB dbHelper = new SqliteGiaoDichDB(this);
    int tienDen = 0;
    int tienDi = 0;
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
        dbHelper = new SqliteGiaoDichDB(MainActivity.this, "GiaoDich", null, 1);
        /*dbHelper.insert(new GiaoDich("Thuế", "26/02/2003", true, "Nguyễn Văn A", 2000));
        dbHelper.insert(new GiaoDich("Thuê nhà", "26/02/2003", true, "Nguyễn Văn B", 2000));
        dbHelper.insert(new GiaoDich("Tiền thường", "26/02/2003", true, "Nguyễn Văn C", 2000));
        dbHelper.insert(new GiaoDich("Tiền thuế", "26/02/2003", false, "Nguyễn Văn D", 2000));
        dbHelper.insert(new GiaoDich("Góp tiền", "26/02/2003", false, "Nguyễn Văn E", 2000));*/
        arrListGiaoDich.addAll(dbHelper.getALLGiaoDich());
        adapter.notifyDataSetChanged();
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        for (int i = 0; i <arrListGiaoDich.size(); i++){
            if (arrListGiaoDich.get(i).isLoaiGiaoDich()){
                tienDen += arrListGiaoDich.get(i).getSoTien();
            } else if (!arrListGiaoDich.get(i).isLoaiGiaoDich()){
                tienDi += arrListGiaoDich.get(i).getSoTien();
            }
        }
        tv_soDu.setText(String.valueOf(tienDen- tienDi));
    }
    private void AnhXa(){
        adapter = new GiaoDichAdapter(this, android.R.layout.simple_list_item_1, arrListGiaoDich);
        lv_lstGiaoDich = this.findViewById(R.id.lv_lstGiaoDich);
        lv_lstGiaoDich.setAdapter(adapter);
        btn_them = this.findViewById(R.id.btn_them);
        tv_soDu = this.findViewById(R.id.tv_soDu);
    }
}