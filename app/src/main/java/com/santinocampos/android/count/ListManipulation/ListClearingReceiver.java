package com.santinocampos.android.count.ListManipulation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by thedr on 3/11/2017.
 */

public class ListClearingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
    }

}
