package com.example.quanlythisinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ThiSinhAdapter extends ArrayAdapter<ThiSinh> {
    public ThiSinhAdapter(@NonNull Context context, int resource, @NonNull List<ThiSinh> objects) {
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ThiSinh ts = getItem(position);
        if (ts != null){
            TextView tv_soBaoDanh = view.findViewById(R.id.tv_soBaoDanh);
            TextView tv_hoTen = view.findViewById(R.id.tv_hoTen);
            TextView tv_tongDiem = view.findViewById(R.id.tv_tongDiem);
            tv_soBaoDanh.setText(ts.getSoBaoDanh());
            tv_hoTen.setText(ts.getHoTen());
            tv_tongDiem.setText(String.valueOf(ts.tongDiem()));
        }
        return view;
    }
}
