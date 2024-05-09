package com.example.doxuantrang_211240555;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DoXuanTrang_Adapter extends ArrayAdapter<Contact_Trang> {
    public DoXuanTrang_Adapter(@NonNull Context context, int resource, @NonNull List<Contact_Trang> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        Contact_Trang person = getItem(position);
        if (person != null){
            TextView v0, v1, v2;
            v0 = view.findViewById(R.id.tv_id);
            v1 = view.findViewById(R.id.tv_name);
            v2 = view.findViewById(R.id.tv_phone);
            v0.setText(String.valueOf(person.getId()));
            v1.setText(person.getName());
            v2.setText(person.getPhone());
        }
        return view;
    }
}
