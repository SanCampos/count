package com.santinocampos.android.count.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.santinocampos.android.count.Models.Currency;
import com.santinocampos.android.count.R;

import java.util.List;

/**
 * Created by thedr on 1/15/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static String LOG_CURRENT_CURRENCY = "CURRENT_CURRENCY";

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
        ListPreference listPreference = ((ListPreference) findPreference("key_change_currency"));
        listPreference.setEntries(Currency.currencyEntries());
        listPreference.setEntryValues(Currency.currencyValues());

        int index = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(getContext().getString(R.string.keyValue_currency), 0);
        listPreference.setValueIndex(index);
    }

    private void updateSummaries() {
        updatePhoneNoPref();
        updateCurrencyListPref();
    }

    private void updateCurrencyListPref() {
        ListPreference listPreference = ((ListPreference) findPreference("key_change_currency"));
        listPreference.setSummary(Currency.values()[Integer.parseInt(listPreference.getValue())].getName());
    }

    private void updatePhoneNoPref() {
        EditTextPreference pref = ((EditTextPreference) findPreference("key_change_phoneNo"));
        pref.setSummary(pref.getText());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("key_change_phoneNo")) {
            EditTextPreference phoneNoPref = (EditTextPreference) findPreference(key);
            String phoneNo = phoneNoPref.getText();
            phoneNoPref.setSummary(phoneNo);
            sharedPreferences.edit().putString("KEY_PHONE_NO", phoneNo).apply();
        } else if (key.equals("key_change_currency")) {
            ListPreference listPreference =  (ListPreference) findPreference(key);
            listPreference.setSummary(Currency.values()[Integer.parseInt(listPreference.getValue())].getName());
            int currentCurrency = sharedPreferences.getInt("KEY_CURRENCY", 0);
            Log.i(LOG_CURRENT_CURRENCY, String.valueOf(currentCurrency));
            sharedPreferences.edit().putInt("KEY_CURRENCY", Integer.parseInt(listPreference.getValue())).apply();
            String value = listPreference.getValue();
            currentCurrency = sharedPreferences.getInt("KEY_CURRENCY", 0);
            Log.i(LOG_CURRENT_CURRENCY, String.valueOf(currentCurrency));
        }
    }
}
