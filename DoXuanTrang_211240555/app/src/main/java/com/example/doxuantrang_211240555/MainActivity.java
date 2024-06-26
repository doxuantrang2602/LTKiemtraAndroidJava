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
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doxuantrang_211240555.broadcast.AlertBroadcastReceiver;
import com.example.doxuantrang_211240555.contentprovider.ShowContact;
import com.example.doxuantrang_211240555.contentprovider.ShowMessage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner spn_sortName;
    MediaPlayer player;
    EditText edt_search;
    Button btn_showContact, btn_showMessage, btn_showCallLog, btn_play, btn_stop;
    ImageButton btn_add;
    ListView lv_lstPerson;
    DoXuanTrang_Sqlite dbHelper = new DoXuanTrang_Sqlite(this);
    List<Contact_Trang> arrayListPerson = new ArrayList<>();
    List<Contact_Trang> originalListPerson = new ArrayList<>();
    AlertBroadcastReceiver broadcastReceiver = new AlertBroadcastReceiver();
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
        originalListPerson.addAll(arrayListPerson);
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
                //filterPhone(s.toString().trim());
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
        spn_sortName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        arrayListPerson.clear();
                        arrayListPerson.addAll(originalListPerson);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        sortByID();
                        break;
                    case 2:
                        sortByName();
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
        }else if (item.getItemId() == R.id.action_search) {
            searchContact(pos);
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
    private void searchContact(int pos) {
        String hoTen = arrayListPerson.get(pos).getName();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + hoTen + "%"};
        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);

        if (cursor != null) {
            StringBuilder result = new StringBuilder();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                result.append("Họ tên: ").append(name).append(", Số điện thoại: ").append(number).append("\n");
            }
            cursor.close();

            if (result.length() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Thông tin");
                builder.setMessage(result.toString());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            } else {
                Toast.makeText(this, "Không tìm thấy contact: " + hoTen, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
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

    private void sortByID() {
        Collections.sort(arrayListPerson, new Comparator<Contact_Trang>() {
            @Override
            public int compare(Contact_Trang o1, Contact_Trang o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortByName() {
        Collections.sort(arrayListPerson, new Comparator<Contact_Trang>() {
            @Override
            public int compare(Contact_Trang o1, Contact_Trang o2) {
                String lastName1 = getLastName(o1.getName());
                String lastName2 = getLastName(o2.getName());
                return lastName1.compareTo(lastName2);
            }
        });
        adapter.notifyDataSetChanged();
    }
    private String getLastName(String fullName) {
        String[] parts = fullName.trim().split("\\s+");
        return parts[parts.length - 1];
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
        spn_sortName = this.findViewById(R.id.spn_sortName);
        String spinnerOption[] = {"Lựa chọn", "Sắp xếp theo mã", "Sắp xếp theo tên"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerOption);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_sortName.setAdapter(spinnerAdapter);
    }
}