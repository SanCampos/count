package com.santinocampos.android.count.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.santinocampos.android.count.Models.Currency;
import com.santinocampos.android.count.R;

import java.util.List;

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
        initListPreference();
        updateSummaries();
    }

    private void initListPreference() {
        ListPreference listPreference = ((ListPreference) findPreference(getContext().getString(R.string.key_currency)));
        listPreference.setEntries(Currency.currencyEntries());
        listPreference.setEntryValues(Currency.currencyValues());

        int index = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(getContext().getString(R.string.keyValue_currency), 0);
        listPreference.setValueIndex(index != 0 ? index : 0);
    }

    private void updateSummaries() {
        EditTextPreference pref = ((EditTextPreference) findPreference(getContext().getString(R.string.key_phoneNo)));
        pref.setSummary(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getString(R.string.keyValue_phoneNo), ""));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getContext().getString(R.string.key_phoneNo))) {
            EditTextPreference phoneNoPref = (EditTextPreference) findPreference(key);
            String phoneNo = phoneNoPref.getText();
            phoneNoPref.setSummary(phoneNo);
            sharedPreferences.edit().putString(getContext().getString(R.string.keyValue_phoneNo), phoneNo).apply();
        } else if (key.equals(getContext().getString(R.string.key_currency))) {
            ListPreference listPreference =  (ListPreference) findPreference(key);
            listPreference.setSummary(Currency.values()[Integer.parseInt(listPreference.getValue())].getName());
            sharedPreferences.edit().putInt(listPreference.getValue(), R.string.keyValue_currency).apply();
        }
    }
}
