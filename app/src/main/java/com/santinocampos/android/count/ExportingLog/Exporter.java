package com.santinocampos.android.count.ExportingLog;

import android.telephony.SmsManager;

import com.santinocampos.android.count.Models.SMSDetails;

/**
 * Created by thedr on 12/15/2016.
 */
public class Exporter {

    public static void export(String list) {
        SmsManager.getDefault()
                  .sendTextMessage(SMSDetails.RECIPIENT_NUMBER, null, list, null, null);
    }
}
