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
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }

    private void updateSummaries() {
        EditTextPreference pref = ((EditTextPreference) findPreference(getString(R.string.key_phoneNo)));
        pref.setSummary(getPreferenceManager().getSharedPreferences().getString(SettingsActivity.KEY_PHONE_NO, ""));
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getContext().getString(R.string.key_phoneNo))) {
            EditTextPreference phoneNoPref = (EditTextPreference) findPreference(key);
            String phoneNo = phoneNoPref.getText();
            phoneNoPref.setSummary(phoneNo);
            sharedPreferences.edit().putString(phoneNo, SettingsActivity.KEY_PHONE_NO).apply();
        }
    }
}
