package com.santinocampos.android.count.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
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
        SmsManager.getDefault()
                  .sendTextMessage(SMSDetails.RECIPIENT_NUMBER, null, createItemList(list, Accountant.get(context)), null, null);
    }

    @NonNull
    private static String createItemList(List<Item> list, Accountant accountant) {
        StringBuilder output = new StringBuilder("Total money" + accountant.getTotalMoneyInformation() + "\n\n");

        for (Item i : list)
            output.append(i.getName())
                    .append(" - ")                             //
                    .append("(")
                    .append(Accountant.countOf(i))
                    .append("x) ")
                    .append(Accountant.individualPriceOf(i))
                    .append("\n")
                    .append(Accountant.totalPriceOf(i))
                    .append("\n\n");

        return output.append("Change left: ").append(accountant.getChange()).toString();
    }
}
