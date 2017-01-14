package com.santinocampos.android.count.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.*;
import com.santinocampos.android.count.R;

/**
 * Created by thedr on 1/13/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG = "SettingsFragment";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        super.onDisplayPreferenceDialog(preference);

        if (preference.getKey().equals(getContext().getString(MyDialogPreference.KEY))) {

        }
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }

    public static String getTagString() {
        return TAG;
    }

}
