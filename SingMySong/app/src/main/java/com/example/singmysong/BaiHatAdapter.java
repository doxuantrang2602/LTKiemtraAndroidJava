package com.example.singmysong;

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

public class BaiHatAdapter extends ArrayAdapter<BaiHat> {
    public BaiHatAdapter(@NonNull Context context, int resource, @NonNull List<BaiHat> objects) {
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
        BaiHat baiHat = getItem(position);
        if (baiHat != null){
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_time = view.findViewById(R.id.tv_time);
            TextView tv_singer = view.findViewById(R.id.tv_singer);
            tv_name.setText(baiHat.getName());
            tv_time.setText(formatTime(baiHat.getTime()));
            tv_singer.setText(baiHat.getSinger());
        }
        return view;
    }
}
