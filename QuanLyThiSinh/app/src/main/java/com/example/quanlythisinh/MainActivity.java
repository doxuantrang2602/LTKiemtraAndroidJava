package com.example.quanlythisinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlythisinh.broadcast.AlertBroadcastReceiver;
import com.example.quanlythisinh.contentprovider.ShowContact;
import com.example.quanlythisinh.contentprovider.ShowMessage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner spn_sapXep;
    FloatingActionButton btn_them;
    Button btn_showContact, btn_showMessage, btn_showCallLog, btn_play, btn_stop;
    EditText edt_search;
    ListView lv_lstThiSinh;
    List<ThiSinh> arrListThiSinh = new ArrayList<>();
    List<ThiSinh> originalListThiSinh = new ArrayList<>();
    ThiSinhAdapter adapter;
    SqliteThiSinhDB dbHelper = new SqliteThiSinhDB(this);
    MediaPlayer player;
    AlertBroadcastReceiver broadcastReceiver = new AlertBroadcastReceiver();
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
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(broadcastReceiver, filter);
    }
    @Override
    protected void onPause(){
        super.onPause();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        getPermission();
        dbHelper = new SqliteThiSinhDB(MainActivity.this, "ThiSinh", null, 1);
        dbHelper.insert(new ThiSinh("MSV001", "Nguyễn Văn B", 9.0f, 8.5f, 8.25f));
        dbHelper.insert(new ThiSinh("MSV002", "Nguyễn Văn D", 8.0f, 8.25f, 8.25f));
        dbHelper.insert(new ThiSinh("MSV003", "Nguyễn Văn C", 8.4f, 7.75f, 8.0f));
        dbHelper.insert(new ThiSinh("MSV004", "Nguyễn Văn A", 9.2f, 8.5f, 8.25f));
        dbHelper.insert(new ThiSinh("MSV005", "Nguyễn Văn F", 8.6f, 9.0f, 7.25f));
        dbHelper.insert(new ThiSinh("211240555", "Đỗ Xuân Tráng", 9.0f, 8f, 8.5f));
        arrListThiSinh.addAll(dbHelper.getAllThiSinh());
        originalListThiSinh.addAll(dbHelper.getAllThiSinh());
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
                filterName(s.toString().trim());
            }
        });
        lv_lstThiSinh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        registerForContextMenu(lv_lstThiSinh);
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        btn_showContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowContact.class);
                startActivity(intent);
            }
        });
        btn_showMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, ShowMessage.class);
                startActivity(intent1);
            }
        });
        btn_showCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] callLog = new String[]{
                        CallLog.Calls.DATE,
                        CallLog.Calls.NUMBER,
                        CallLog.Calls.DURATION
                };
                Cursor c = getContentResolver().query(
                        CallLog.Calls.CONTENT_URI,
                        callLog,
                        CallLog.Calls.DURATION + "<?",
                        new String[]{"ID"},
                        CallLog.Calls.DATE + " Asc"
                );
                c.moveToFirst();
                String s = "";
                while (!c.isAfterLast()){
                    for (int i = 0; i < c.getColumnCount(); i++){
                        s = s + c.getString(i) + "-";
                    }
                    s = s + "\n";
                    c.moveToNext();
                }
                c.close();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = MediaPlayer.create(MainActivity.this, Settings.System.DEFAULT_RINGTONE_URI);
                player.setLooping(true);
                player.start();
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
            }
        });
        spn_sapXep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        arrListThiSinh.clear();
                        arrListThiSinh.addAll(originalListThiSinh);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        sapXepTongDiem();
                        break;
                    case 2:
                        sapXepDiemTB();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.READ_CONTACTS
            }, 999);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.WRITE_CONTACTS
            }, 999);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.READ_SMS
            }, 999);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_CALL_LOG
            }, 999);
        }
    }
    private void sapXepTongDiem() {
        Collections.sort(arrListThiSinh, new Comparator<ThiSinh>() {
            @Override
            public int compare(ThiSinh o1, ThiSinh o2) {
                return Float.compare(o1.tongDiem(), o2.tongDiem());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sapXepDiemTB() {
        Collections.sort(arrListThiSinh, new Comparator<ThiSinh>() {
            @Override
            public int compare(ThiSinh o1, ThiSinh o2) {
                return Float.compare(o1.diemTB(), o2.diemTB());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sapXepTheoTen() {
        Collections.sort(arrListThiSinh, new Comparator<ThiSinh>() {
            @Override
            public int compare(ThiSinh o1, ThiSinh o2) {
                String lastName1 = getLastName(o1.getHoTen());
                String lastName2 = getLastName(o2.getHoTen());
                return lastName1.compareTo(lastName2);
            }
        });
        adapter.notifyDataSetChanged();
    }
    private String getLastName(String fullName) {
        String[] parts = fullName.trim().split("\\s+");
        return parts[parts.length - 1];
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
            intent.putExtra("sobaodanh", arrListThiSinh.get(pos).getSoBaoDanh());
            intent.putExtra("hoten", arrListThiSinh.get(pos).getHoTen());
            intent.putExtra("toan", arrListThiSinh.get(pos).getToan());
            intent.putExtra("ly", arrListThiSinh.get(pos).getLy());
            intent.putExtra("hoa", arrListThiSinh.get(pos).getHoa());
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_delete){
            confirmDelete(pos);
            return true;
        }
        return super.onContextItemSelected(item);
    }
    private void confirmDelete(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Đỗ Xuân Tráng wants to delete?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String soBaoDanh = arrListThiSinh.get(pos).getSoBaoDanh();
                arrListThiSinh.remove(pos);
                dbHelper.delete(soBaoDanh);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void filterName(String word) {
        Cursor cursor = dbHelper.searchByName(word);
        ArrayList<ThiSinh> filterList = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                String soBaoDanh = cursor.getString(0);
                String hoTen = cursor.getString(1);
                float toan = cursor.getFloat(2);
                float ly = cursor.getFloat(3);
                float hoa = cursor.getFloat(4);
                filterList.add(new ThiSinh(soBaoDanh, hoTen, toan, ly, hoa));
            }
            cursor.close();
        }
        arrListThiSinh.clear();
        arrListThiSinh.addAll(filterList);
        adapter.notifyDataSetChanged();
    }

    private void AnhXa(){
        adapter = new ThiSinhAdapter(this, android.R.layout.simple_list_item_1, arrListThiSinh);
        lv_lstThiSinh = this.findViewById(R.id.lv_lstThiSinh);
        lv_lstThiSinh.setAdapter(adapter);
        spn_sapXep = this.findViewById(R.id.spn_sapXep);
        String spinnerOption[] = {"Lựa chọn", "Sắp xếp tăng dần theo tổng diểm", "Sắp xếp tăng dần theo điểm trung bình"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerOption);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_sapXep.setAdapter(spinnerAdapter);
        btn_them = this.findViewById(R.id.btn_them);
        btn_showContact = this.findViewById(R.id.btn_showContact);
        btn_showMessage = this.findViewById(R.id.btn_showMessage);
        btn_showCallLog = this.findViewById(R.id.btn_showCallLog);
        btn_play = this.findViewById(R.id.btn_play);
        btn_stop = this.findViewById(R.id.btn_stop);
        edt_search = this.findViewById(R.id.edt_search);
    }
}