package com.santinocampos.android.count.ListManipulation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.santinocampos.android.count.Models.Accountant;

import io.realm.Realm;

/**
 * Created by thedr on 3/11/2017.
 */

public class ListClearingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "DELETING", Toast.LENGTH_SHORT).show();
        Realm.init(context);
        Realm realm = Realm.getInstance(Accountant.getRealmConfiguration());
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

}
