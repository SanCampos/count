package com.santinocampos.android.count.Settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.santinocampos.android.count.R;

/**
 * Created by thedr on 1/15/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_PHONE_NO = "KEY_PHONE_NO";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.settings_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
