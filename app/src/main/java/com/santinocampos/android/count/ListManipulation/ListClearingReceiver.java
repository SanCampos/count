package com.santinocampos.android.count.ListManipulation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.santinocampos.android.count.Models.ApplicationContext;

import io.realm.Realm;

/**
 * Created by thedr on 3/11/2017.
 */

public class ListClearingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "DELETING", Toast.LENGTH_SHORT).show();
        Realm.init(ApplicationContext.get());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

}
