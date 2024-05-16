package com.example.dekhachsan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter_262 extends ArrayAdapter<HoaDon_26> {
    public Adapter_262(@NonNull Context context, int resource, @NonNull List<HoaDon_26> objects) {
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        HoaDon_26 hoaDon = getItem(position);
        if (hoaDon != null){
            TextView v0, v1, v2;
            v0 = view.findViewById(R.id.tv_hoTen);
            v1 = view.findViewById(R.id.tv_soPhong);
            v2 = view.findViewById(R.id.tv_tongTien);
            v0.setText(String.valueOf(hoaDon.getHoTen()));
            v1.setText(String.valueOf(hoaDon.getSoPhong()));
            v2.setText(String.valueOf(hoaDon.tongTien()));
        }
        return view;
    }
}