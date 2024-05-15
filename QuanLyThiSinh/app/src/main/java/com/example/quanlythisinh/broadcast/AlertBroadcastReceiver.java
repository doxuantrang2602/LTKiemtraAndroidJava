package com.example.quanlythisinh.broadcast;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.widget.Toast;

public class AlertBroadcastReceiver extends BroadcastReceiver {
    private boolean disconnected = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action != null) {
            switch (action) {
                case ConnectivityManager.CONNECTIVITY_ACTION:
                    handleInternetConnection(context);
                    break;
                case Intent.ACTION_BATTERY_CHANGED:
                    handleBatteryLevel(context, intent);
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    handleBluetoothState(context, intent);
                    break;
            }
        }
    }
    private void handleInternetConnection(Context context) {
        if (!isConnectedToInternet(context)) {
            disconnected = true;
            Toast.makeText(context, "Mất kết nối internet", Toast.LENGTH_LONG).show();
        } else {
            if (disconnected) {
                Toast.makeText(context, "Đã kết nối lại internet", Toast.LENGTH_LONG).show();
                disconnected = false;
            }
        }
    }

    private void handleBatteryLevel(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        if (level <= 15) {
            Toast.makeText(context, "Pin yếu", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void handleBluetoothState(Context context, Intent intent) {
        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
        switch (state) {
            case BluetoothAdapter.STATE_OFF:
                Toast.makeText(context, "Bluetooth đã tắt", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                Toast.makeText(context, "Bluetooth đang được tắt", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.STATE_ON:
                Toast.makeText(context, "Bluetooth đã bật", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                Toast.makeText(context, "Bluetooth đang được bật", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
