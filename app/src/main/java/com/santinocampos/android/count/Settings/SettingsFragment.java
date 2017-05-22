package com.santinocampos.android.count.Settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;


import com.santinocampos.android.count.ListManipulation.ListClearingAlarm;
import com.santinocampos.android.count.Models.Currency;
import com.santinocampos.android.count.R;
import static com.santinocampos.android.count.Utils.NumberUtils.MONEY.*;

/**
 * Created by thedr on 1/15/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static String LOG_CURRENT_CURRENCY = "CURRENT_CURRENCY";
    private static String LOG_ISCLEARLIST_CHECKED = "IS_CHECKED";

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
        listPreference.setEntryValues(Currency.currencyValues()); /** There's got to be a better way to do this **/
                                                                  /** Anyway to instantiate an array as a sequence of integers? **/

        int index = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("KEY_CURRENCY", 0);
        listPreference.setValueIndex(index);
    }

    private void updateSummaries() {
        updatePhoneNoPref();
        updateCurrencyListPref();
        updateClearListPreference();
    }

    private void updateCurrencyListPref() {
        ListPreference listPreference = ((ListPreference) findPreference("key_change_currency"));
        listPreference.setSummary(Currency.values()[Integer.parseInt(listPreference.getValue())].getName());
    }

    private void updatePhoneNoPref() {
        EditTextPreference pref = ((EditTextPreference) findPreference("key_change_phoneNo"));
        pref.setSummary(pref.getText());
    }

    private void updateClearListPreference() {
        CheckBoxPreference listClearPreference = ((CheckBoxPreference) findPreference("key_change_clearList"));
        boolean isChecked = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("KEY_CLEARLIST", false);
        listClearPreference.setChecked(isChecked);
    }

        @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
                case "key_change_phoneNo":
                    EditTextPreference phoneNoPref = (EditTextPreference) findPreference(key);
                    String phoneNo = phoneNoPref.getText();
                    phoneNoPref.setSummary(phoneNo);
                    sharedPreferences.edit().putString("KEY_PHONE_NO", phoneNo).apply();
                    break;
                case "key_change_currency":
                    ListPreference listPreference = (ListPreference) findPreference(key);
                    int chosenCurrency = Integer.parseInt(listPreference.getValue());
                    listPreference.setSummary(Currency.values()[chosenCurrency].getName());
                    sharedPreferences.edit().putInt("KEY_CURRENCY", chosenCurrency).apply();
                    setCurrentCurrencySymbol(getContext());
                    break;
                case "key_change_clearList":
                    CheckBoxPreference clearListPreference = ((CheckBoxPreference) findPreference(key));
                    boolean isChecked = clearListPreference.isChecked();
                    sharedPreferences.edit().putBoolean("KEY_CLEARLIST", isChecked).apply();
                    checkToStartClearService(getContext());
                    break;
            }
    }

    private void checkToStartClearService(Context context) {
        Toast.makeText(context, "FIRING AWAY", Toast.LENGTH_SHORT).show();
        ListClearingAlarm.initAlarm(context);
    }
}
