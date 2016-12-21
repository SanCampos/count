package com.santinocampos.android.count.ExportingLog;

import android.content.Context;
import android.telephony.SmsManager;

import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.Models.SMSDetails;

import java.util.List;

/**
 * Created by thedr on 12/15/2016.
 */
public class Exporter {

    public static void exportItemList(List<Item> list, Context context) {
        StringBuilder output = new StringBuilder("");
        Accountant accountant = Accountant.get(context);

        for (Item i : list)
            output.append(i.getName())
                    .append(" - ")
                    .append("(" + accountant.countOf(i) + "x) ")
                    .append(accountant.individualPriceOf(i) + "\n")
                    .append(accountant.totalPriceOf(i) + "\n\n");

        SmsManager.getDefault()
                  .sendTextMessage(SMSDetails.RECIPIENT_NUMBER, null, output.toString(), null, null);
    }
}
