package com.example.lesson4test_listview_spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv_monHoc;
    Spinner spn_monHoc;
    Button btn_Them, btn_Xoa, btn_Login, btn_Send;
    EditText edt_monHoc;
    BottomNavigationView mNavigationView;
    ArrayList<String> arrListMonHoc = new ArrayList<>();
    ArrayAdapter<String> adapter;
    int val;
    Switch sw_action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitWidget();
        sw_action.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        String arrMon[] = {"Java", "C#", "Python", "C++", "Dart"};
        arrListMonHoc.add(arrMon[0]);
        arrListMonHoc.add(arrMon[1]);
        arrListMonHoc.add(arrMon[2]);
        arrListMonHoc.add(arrMon[3]);
        arrListMonHoc.add(arrMon[4]);
        adapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1,
                arrListMonHoc
        );
        lv_monHoc.setAdapter(adapter);
        spn_monHoc.setAdapter(adapter);
        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monHoc = edt_monHoc.getText().toString();
                if (monHoc.equals("")){
                    Toast.makeText(MainActivity.this, "Không được bỏ trống !",Toast.LENGTH_LONG).show();
                }else {
                    showConfirmationDialog(monHoc);
                }
            }
        });
        lv_monHoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, ""+arrListMonHoc.get(position), Toast.LENGTH_LONG).show();
                edt_monHoc.setText(arrListMonHoc.get(position));
                val = position;
                xacNhanXoa(arrListMonHoc.get(position));
            }
        });
        spn_monHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLoginDialog();
            }
        });
        btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                String ht = edt_monHoc.getText().toString();
                /*Bundle bundle = new Bundle();
                bundle.putString("mon", ht);*/
                intent.putExtra("mon", ht);
                //intent.putExtra("hoten", ht);
                startActivity(intent);
            }
        });
    }
    private void callLoginDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_account);
        EditText edt_username = (EditText) dialog.findViewById(R.id.edt_username);
        EditText edt_password = (EditText) dialog.findViewById(R.id.edt_password);
        Button btn_ok = (Button)dialog.findViewById(R.id.btn_Ok);
        Button btn_cancel = (Button)dialog.findViewById(R.id.btn_Cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edt_username.getText().toString();
                String passWord = edt_password.getText().toString();
                if(userName.equals("xuantrangdo") && passWord.equals("12345")){
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }
    private void xacNhanXoa(String mon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa "+mon+" không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrListMonHoc.remove(val);
                Toast.makeText(MainActivity.this, "Xóa thành công !", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showConfirmationDialog(final String monHoc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có muốn thêm "+monHoc+" không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrListMonHoc.add(monHoc);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Thêm thành công !", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.mn_green){
            mNavigationView.setBackgroundColor(Color.GREEN);
        } else if (item.getItemId() == R.id.mn_red) {
            mNavigationView.setBackgroundColor(Color.RED);
        } else if (item.getItemId() == R.id.mn_blue) {
            mNavigationView.setBackgroundColor(Color.BLUE);
        }
        return super.onOptionsItemSelected(item);
    }
    private void InitWidget() {
        btn_Them = this.findViewById(R.id.btn_Them);
        btn_Xoa = this.findViewById(R.id.btn_Xoa);
        btn_Login = this.findViewById(R.id.btn_Signin);
        btn_Send = this.findViewById(R.id.btn_Send);
        edt_monHoc = this.findViewById(R.id.edt_MonHoc);
        lv_monHoc = this.findViewById(R.id.lv_monHoc);
        spn_monHoc = this.findViewById(R.id.spn_monHoc);
        sw_action = this.findViewById(R.id.sw_action);
        mNavigationView = this.findViewById(R.id.bottom_nav);
    }
}