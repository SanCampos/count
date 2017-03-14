package com.santinocampos.android.count.ListManipulation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

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
        Intent clearListIntent = new Intent(context, ListClearingReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, 192837, clearListIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarm.set(AlarmManager.RTC_WAKEUP, broadcastTime.getTimeInMillis(), alarmPendingIntent);
    }
}
