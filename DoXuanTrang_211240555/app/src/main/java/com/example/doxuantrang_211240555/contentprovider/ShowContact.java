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
import android.widget.AdapterView;
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
                String phoneNumber = edt_Phone.getText().toString().trim();
                if (!phoneNumber.isEmpty()) {
                    Uri phoneUri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
                    Cursor cursor = getContentResolver().query(phoneUri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
                            getContentResolver().delete(contactUri, null, null);
                        } while (cursor.moveToNext());
                        cursor.close();
                        Toast.makeText(ShowContact.this, "Đã xóa contact có số điện thoại " + phoneNumber, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ShowContact.this, "Không tìm thấy contact có số điện thoại " + phoneNumber, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ShowContact.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                String[] parts = selectedItem.split("-");
                String contactId = parts[0].trim();
                Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int nameColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    String name = cursor.getString(nameColumnIndex);
                    edt_Name.setText(name);
                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[] { contactId },
                            null
                    );
                    if (phoneCursor != null && phoneCursor.moveToFirst()) {
                        int phoneColumnIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String phone = phoneCursor.getString(phoneColumnIndex);
                        edt_Phone.setText(phone);
                        phoneCursor.close();
                    }
                    cursor.close();
                }
            }
        });
        showContact2();
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
    private void showContact2() {
        ArrayList<String> list = new ArrayList<>();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(
                uri,
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER},
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String contactInfo = contactId + " - " + displayName + " - " + phoneNumber;
                list.add(contactInfo);
            }
            cursor.close();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, list
        );
        lv_contact.setAdapter(adapter);
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