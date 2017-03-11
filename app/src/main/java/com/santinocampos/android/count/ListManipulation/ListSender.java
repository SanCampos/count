package com.santinocampos.android.count.ListManipulation;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.util.Log;

import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.R;
import com.santinocampos.android.count.Settings.SettingsActivity;

import java.util.List;

/**
 * Created by thedr on 12/15/2016.
 */
public class ListSender {

    public static void exportItemList(List<Item> list, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String phoneNo = preferences.getString("KEY_PHONE_NO", "");
        SmsManager.getDefault()
                  .sendTextMessage(phoneNo, null, createItemList(list, Accountant.get(context), context), null, null);
    }

    @NonNull
    private static String createItemList(List<Item> list, Accountant accountant, Context context) {
        StringBuilder output = new StringBuilder(context.getString(R.string.text_total_money) + " " + accountant.getTotalMoneyInformation() + "\n\n");

        for (Item i : list)
            output.append(i.getName())
                    .append(" - ")                             //
                    .append("(")
                    .append(accountant.countOf(i))
                    .append("x) ")
                    .append(accountant.individualPriceOf(i))
                    .append("\n")
                    .append(accountant.totalPriceOf(i))
                    .append("\n\n");

        output.append(context.getString(R.string.text_change_left)).append(" ").append(accountant.getChange());
        return output.toString();
    }
}
