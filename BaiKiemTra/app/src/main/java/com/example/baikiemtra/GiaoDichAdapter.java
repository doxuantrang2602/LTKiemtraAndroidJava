package com.example.baikiemtra;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class GiaoDichAdapter extends ArrayAdapter<GiaoDich> {
    public GiaoDichAdapter(@NonNull Context context, int resource, @NonNull List<GiaoDich> objects) {
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        GiaoDich giaoDich = getItem(position);
        if (giaoDich != null){
            LinearLayout ln_item = view.findViewById(R.id.ln_item);
            TextView tv_loaiGiaoDich = view.findViewById(R.id.tv_loaiGiaoDich);
            if (giaoDich.isLoaiGiaoDich()){
                tv_loaiGiaoDich.setText("Tiền đến từ:");
            } else {
                tv_loaiGiaoDich.setText("Tiền đi tới:");
                ln_item.setBackgroundColor(Color.RED);
            }
            TextView tv_hoTen = view.findViewById(R.id.tv_hoTen);
            TextView tv_noiDung = view.findViewById(R.id.tv_noiDung);
            TextView tv_ngay = view.findViewById(R.id.tv_ngay);
            TextView tv_tongTien = view.findViewById(R.id.tv_tongTien);

            tv_hoTen.setText(giaoDich.getHoTen());
            tv_noiDung.setText(giaoDich.getNoiDung());
            tv_ngay.setText(giaoDich.getNgay());
            tv_tongTien.setText(String.valueOf(giaoDich.getSoTien()));
        }
        return view;
    }
}