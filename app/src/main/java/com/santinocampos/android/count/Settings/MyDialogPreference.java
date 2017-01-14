package com.santinocampos.android.count.Settings;

import android.content.Context;
import android.preference.DialogPreference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.santinocampos.android.count.R;

import java.util.Set;

/**
 * Created by thedr on 1/13/2017.
 */

public class MyDialogPreference extends DialogPreference {

    public static final int KEY = R.string.preference_phoneNumber;

    private EditText mPhoneNumberEditText;

    public MyDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);
        setKey(getContext().getString(KEY));
        String summary = getSharedPreferences().getString(SettingsActivity.PHONE_NUMBER_KEY, "");
        setSummary(summary);
    }

    public void onBindDialogView(View view) {
        super.onBindDialogView(view);
        mPhoneNumberEditText = ((EditText) view.findViewById(R.id.editText_changeNumber));
    }

    public void onDialogClosed(boolean positive) {
        getSharedPreferences()
                .edit()
                .putString(SettingsActivity.PHONE_NUMBER_KEY, mPhoneNumberEditText.getText().toString())
                .apply();
        super.onDialogClosed(positive);
        persistBoolean(positive);
    }
}
