package com.example.doxuantrang_211240555;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doxuantrang_211240555.broadcast.AlertBroadcastReceiver;
import com.example.doxuantrang_211240555.contentprovider.ShowContact;
import com.example.doxuantrang_211240555.contentprovider.ShowMessage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    EditText edt_search;
    Button btn_showContact, btn_showMessage, btn_showCallLog, btn_play, btn_stop;
    FloatingActionButton btn_add;
    ListView lv_lstPerson;
    DoXuanTrang_Sqlite dbHelper = new DoXuanTrang_Sqlite(this);
    List<Contact_Trang> arrayListPerson = new ArrayList<>();
    AlertBroadcastReceiver broadcastReceiver;
    DoXuanTrang_Adapter adapter;
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
        dbHelper = new DoXuanTrang_Sqlite(MainActivity.this, "Person", null, 1);
        dbHelper.insert(new Contact_Trang(1, "Nguyễn Văn A", "011111111"));
        dbHelper.insert(new Contact_Trang(2, "Nguyễn Văn B", "022222222"));
        dbHelper.insert(new Contact_Trang(3, "Nguyễn Văn C", "033333333"));
        dbHelper.insert(new Contact_Trang(4, "Đỗ Xuân Tráng", "044444444"));
        dbHelper.insert(new Contact_Trang(5, "Nguyễn Văn D", "055555555"));
        dbHelper.insert(new Contact_Trang(6, "Nguyễn Văn E", "066666666"));
        arrayListPerson.addAll(dbHelper.getAllPerson());
        adapter.notifyDataSetChanged();
        lv_lstPerson.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        registerForContextMenu(lv_lstPerson);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
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

    private void filterName(String word) {
        Cursor cursor = dbHelper.searchByName(word);
        ArrayList<Contact_Trang> filterList = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                filterList.add(new Contact_Trang(id, name, phone));
            }
            cursor.close();
        }
        arrayListPerson.clear();
        arrayListPerson.addAll(filterList);
        adapter.notifyDataSetChanged();
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
            intent.putExtra("id", arrayListPerson.get(pos).getId());
            intent.putExtra("name", arrayListPerson.get(pos).getName());
            intent.putExtra("phone", arrayListPerson.get(pos).getPhone());
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
                Contact_Trang person = arrayListPerson.get(pos);
                arrayListPerson.remove(person);
                dbHelper.delete(person.getId(), person);
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
    private void AnhXa(){
        adapter = new DoXuanTrang_Adapter(this, R.layout.item, arrayListPerson);
        edt_search = this.findViewById(R.id.edt_search);
        btn_showContact = this.findViewById(R.id.btn_showContact);
        btn_showMessage = this.findViewById(R.id.btn_showMessage);
        btn_showCallLog = this.findViewById(R.id.btn_showCallLog);
        btn_play = this.findViewById(R.id.btn_play);
        btn_stop = this.findViewById(R.id.btn_stop);
        btn_add = this.findViewById(R.id.btn_add);
        lv_lstPerson = this.findViewById(R.id.lv_lstPerson);
        lv_lstPerson.setAdapter(adapter);
    }
}