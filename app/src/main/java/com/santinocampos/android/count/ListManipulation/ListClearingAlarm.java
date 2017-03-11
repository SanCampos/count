package com.santinocampos.android.count.ListManipulation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by thedr on 3/11/2017.
 */

public class ListClearingAlarm {
    private static AlarmManager alarm;

    public static void initAlarm(Context context) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 2);

        alarm = ((AlarmManager) context.getSystemService(ALARM_SERVICE));
        Intent intent = new Intent(context, ListClearingReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}
