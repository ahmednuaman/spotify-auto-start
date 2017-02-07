package com.ahmednuaman.spotifyautostart.spotifyauto_start;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

public class BluetoothService extends IntentService {
    private String selectedBluetoothDevice;

    public BluetoothService() {
        super("BluetoothService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        readPreference();
        setUpIntent();
    }

    @Override
    public void onHandleIntent(Intent intent) {
        return;
    }

    private void readPreference() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String key = getString(R.string.selected_bluetooth_device);
        this.selectedBluetoothDevice = sharedPref.getString(key, null);
    }

    private void setUpIntent() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        registerReceiver(receiver, filter);
    }

    private void openSpotify() {

    }

    private void getConnectedBluetoothDevices() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> boundDevices = bluetoothAdapter.getBondedDevices();

        for (BluetoothDevice boundDevice : boundDevices) {
            if (boundDevice.getAddress().equals(this.selectedBluetoothDevice)) {
                openSpotify();
                return;
            }
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_CONNECTED:
                        getConnectedBluetoothDevices();
                        break;
                }
            }
        }
    }
}
