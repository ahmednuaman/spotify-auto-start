package com.ahmednuaman.spotifyautostart.spotifyauto_start;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

public class BluetoothService extends IntentService {
    private static final String OPEN_SPOTIFY = "com.ahmednuaman.spotifyautostart.spotifyauto_start.action.OPEN_SPOTIFY";

    public BluetoothService() {
        super("BluetoothService");
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
}
