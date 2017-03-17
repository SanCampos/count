package com.santinocampos.android.count.ListManipulation;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.santinocampos.android.count.Controllers.CounterActivity;
import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.R;

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

            if (!realm.where(Item.class).findAll().isEmpty()) {
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();
                makeNotification(context);
            }

        }

        private void makeNotification(Context context) {
            int notificationID = 001;
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                                                                         .setSmallIcon(R.drawable.ic_notfcn)
                                                                         .setContentTitle(context.getString(R.string.notn_title_clear))
                                                                         .setContentInfo(context.getString(R.string.notn_content_clear));

            Intent resultIntent = new Intent(context, CounterActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);

            NotificationManager mNotifyManager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
            mNotifyManager.notify(notificationID, mBuilder.build());
        }
    }
}


