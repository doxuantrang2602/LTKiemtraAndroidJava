package com.example.doxuantrang_211240555.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doxuantrang_211240555.MainActivity;
import com.example.doxuantrang_211240555.R;

import java.util.ArrayList;

public class ShowContact extends AppCompatActivity {
    EditText edt_Name, edt_Phone;
    Button btn_addContact, btn_deleteContact, btn_back1;
    ListView lv_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        AnhXa();
        btn_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowContact.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_Name.getText().toString().trim();
                String phone = edt_Phone.getText().toString().trim();
                if (!name.isEmpty() && !phone.isEmpty()) {
                    addContact(name, phone);
                    Toast.makeText(ShowContact.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowContact.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        showContact();
    }
    private void showContact() {
        ArrayList<String> list = new ArrayList<>();
        // Lấy uri
        Uri uri = Uri.parse("content://contacts/people");
        Cursor c1 = getContentResolver().query(uri, null, null, null);
        c1.moveToNext();
        while (!c1.isAfterLast()){
            String s = "";
            String idColumnName = ContactsContract.Contacts._ID;
            int idIndex = c1.getColumnIndex(idColumnName);
            s = c1.getString(idIndex) + "-";
            String nameColumnName = ContactsContract.Contacts.DISPLAY_NAME;
            int nameIndex = c1.getColumnIndex(nameColumnName);
            s = s + c1.getString(nameIndex);
            list.add(s);
            c1.moveToNext();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1, list
            );
            lv_contact.setAdapter(adapter);
        }
    }
    private void addContact(String name, String phone) {
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();

        Uri rawContactUri = cr.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name);
        cr.insert(ContactsContract.Data.CONTENT_URI, values);
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        cr.insert(ContactsContract.Data.CONTENT_URI, values);
    }

    private void AnhXa(){
        edt_Name = this.findViewById(R.id.edt_Name);
        edt_Phone = this.findViewById(R.id.edt_Phone);
        btn_back1 = this.findViewById(R.id.btn_back1);
        btn_addContact = this.findViewById(R.id.btn_addContact);
        btn_deleteContact = this.findViewById(R.id.btn_deleteContact);
        lv_contact = this.findViewById(R.id.lv_conTact);
    }
}