package com.santinocampos.android.count.ListManipulation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.santinocampos.android.count.Models.Accountant;

import java.util.Calendar;

import io.realm.Realm;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by thedr on 3/11/2017.
 */

public class ListClearingAlarm {
    private static AlarmManager alarm;

    public static void initAlarm(Context context) {
        Calendar broadcastTime = Calendar.getInstance();
        broadcastTime.add(Calendar.SECOND, 10);

        alarm = ((AlarmManager) context.getSystemService(ALARM_SERVICE));
        Intent clearListIntent = new Intent(context, ListClearingAlarm.ListClearingReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, 192837, clearListIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarm.set(AlarmManager.RTC_WAKEUP, broadcastTime.getTimeInMillis(), alarmPendingIntent);
        Toast.makeText(context, "Alarm set", Toast.LENGTH_SHORT).show();
    }

    public static class ListClearingReceiver extends BroadcastReceiver {

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
}


