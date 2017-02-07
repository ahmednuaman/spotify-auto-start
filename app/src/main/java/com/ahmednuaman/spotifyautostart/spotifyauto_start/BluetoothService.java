package com.ahmednuaman.spotifyautostart.spotifyauto_start;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;

import java.util.Set;

public class BluetoothService extends IntentService {
    private static final String OPEN_SPOTIFY = "com.ahmednuaman.spotifyautostart.spotifyauto_start.action.OPEN_SPOTIFY";

    public BluetoothService() {
        super("BluetoothService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        registerReceiver(receiver, filter);
    }

    public static void startActionOpenSpotify(Context context) {
        Intent intent = new Intent(context, BluetoothService.class);
        intent.setAction(OPEN_SPOTIFY);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (OPEN_SPOTIFY.equals(action)) {
                handleActionOpenSpotify();
            }
        }
    }

    private void handleActionOpenSpotify() {
    }

    private void getConnectedBluetoothDevices() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> boundDevices = bluetoothAdapter.getBondedDevices();


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
