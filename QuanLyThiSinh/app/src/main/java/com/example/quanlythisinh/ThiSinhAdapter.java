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
            TextView v0, v1, v2;
            v0 = view.findViewById(R.id.tv_soBaoDanh);
            v1 = view.findViewById(R.id.tv_hoTen);
            v2 = view.findViewById(R.id.tv_tongDiem);
            v0.setText(ts.getSoBaoDanh());
            v1.setText(ts.getHoTen());
            v2.setText(String.valueOf(ts.tongDiem()));
        }
        return view;
    }
}
