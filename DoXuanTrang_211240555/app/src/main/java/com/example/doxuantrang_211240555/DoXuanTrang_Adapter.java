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
import java.util.Locale;

public class DoXuanTrang_Adapter extends ArrayAdapter<Contact_Trang> {
    public DoXuanTrang_Adapter(@NonNull Context context, int resource, @NonNull List<Contact_Trang> objects) {
        super(context, resource, objects);
    }
    private String formatTime(float time) {
        int minutes = (int) time;
        int seconds = Math.round((time - minutes) * 60);
        return String.format(Locale.getDefault(), "%d:%02d", minutes, seconds);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        Contact_Trang person = getItem(position);
        if (person != null){
            TextView tv_id = view.findViewById(R.id.tv_id);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_phone = view.findViewById(R.id.tv_phone);
            tv_id.setText(String.valueOf(person.getId()));
            tv_name.setText(person.getName());
            tv_phone.setText(person.getPhone());
        }
        return view;
    }
}
