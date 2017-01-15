package com.santinocampos.android.count.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.santinocampos.android.count.R;

/**
 * Created by thedr on 1/15/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(R.string.key_phoneNo)) {
            EditTextPreference phoneNoPref = (EditTextPreference) findPreference(key);
            String phoneNo = phoneNoPref.getText();
            phoneNoPref.setSummary(phoneNo);
            sharedPreferences.edit().putString(phoneNo, SettingsActivity.KEY_PHONE_NO).apply();
        }
    }
}
