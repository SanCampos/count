package com.santinocampos.android.count.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

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
        updateSummaries();
    }

    private void updateSummaries() {
        EditTextPreference pref = ((EditTextPreference) findPreference(getString(R.string.key_phoneNo)));
        pref.setSummary(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getString(R.string.keyValue_phoneNo), ""));
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getContext().getString(R.string.key_phoneNo))) {
            EditTextPreference phoneNoPref = (EditTextPreference) findPreference(key);
            String phoneNo = phoneNoPref.getText();
            phoneNoPref.setSummary(phoneNo);
            sharedPreferences.edit().putString(getString(R.string.keyValue_phoneNo), phoneNo).apply();
        }
    }
}
