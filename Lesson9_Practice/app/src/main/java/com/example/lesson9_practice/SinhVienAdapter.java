package com.example.lesson9_practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SinhVienAdapter extends ArrayAdapter<SinhVien> {

    public SinhVienAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public SinhVienAdapter(@NonNull Context context, int resource, @NonNull List<SinhVien> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater =LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.sinhvien_item, null);
        }
        SinhVien p = getItem(position);
        if(p != null){
            TextView v0 =(TextView) v.findViewById(R.id.tv_id);
            TextView v1 =(TextView)v.findViewById(R.id.tv_hoTen);
            TextView v2 =(TextView)v.findViewById(R.id.tv_namSinh);
            v0.setText(String.valueOf(p.getId()));
            v1.setText(p.getName());
            v2.setText(String.valueOf(p.getBirthyear()));
        }
        return v;
    }
}
