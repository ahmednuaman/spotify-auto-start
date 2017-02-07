package com.ahmednuaman.spotifyautostart.spotifyauto_start;


import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SettingsActivity extends AppCompatPreferenceActivity {
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
            }

            Context context = preference.getContext();
            SharedPreferences sharedPref = context.getSharedPreferences(
                    context.getString(R.string.pref_file_key), Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(context.getString(R.string.selected_bluetooth_device), stringValue);
            editor.commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDefaultFragment();
    }

    private void initDefaultFragment() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new GeneralPreferenceFragment()).commit();
    }

    @Override
    public boolean onIsMultiPane() {
        return false;
    }

    protected boolean isValidFragment(String fragmentName) {
        return GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(false);
            bindBluetoothDevices();
        }

        private void bindBluetoothDevices() {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> boundDevices = bluetoothAdapter.getBondedDevices();
            ListPreference blueboothDevices = (ListPreference) findPreference("blueetooth_devices");

            CharSequence entries[] = new String[boundDevices.size()];
            CharSequence entryValues[] = new String[boundDevices.size()];
            int i = 0;

            for (BluetoothDevice bluetoothDevice : boundDevices) {
                entries[i] = bluetoothDevice.getName();
                entryValues[i] = bluetoothDevice.getAddress();

                i++;
            }

            blueboothDevices.setEntries(entries);
            blueboothDevices.setEntryValues(entryValues);
        }

//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            int id = item.getItemId();
//            if (id == android.R.id.home) {
//                startActivity(new Intent(getActivity(), SettingsActivity.class));
//                return true;
//            }
//            return super.onOptionsItemSelected(item);
//        }
    }
}
