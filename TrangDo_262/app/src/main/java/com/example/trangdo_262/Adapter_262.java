package com.example.trangdo_262;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter_262 extends ArrayAdapter<Taxi_262> {
    public Adapter_262(@NonNull Context context, int resource, @NonNull List<Taxi_262> objects) {
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        Taxi_262 taxi = getItem(position);
        if (taxi != null){
            TextView v0, v1, v2;
            v0 = view.findViewById(R.id.tv_soXe);
            v1 = view.findViewById(R.id.tv_quangDuong);
            v2 = view.findViewById(R.id.tv_tongTien);
            v0.setText(taxi.getSoXe());
            v1.setText(String.valueOf(taxi.getQuangDuong()));
            v2.setText(String.valueOf(taxi.tongTien()));
        }
        return view;
    }
}
