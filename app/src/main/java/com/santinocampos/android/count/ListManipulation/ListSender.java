package com.santinocampos.android.count.ListManipulation;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.R;
import com.santinocampos.android.count.Settings.SettingsActivity;

import java.util.List;

import static com.santinocampos.android.count.Utils.NumberUtils.MONEY.money;

/**
 * Created by thedr on 12/15/2016.
 */
public class ListSender {

    public static void exportItemList(Accountant accountant, Context context) {
        List<Item> list = accountant.getItemList();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String phoneNo = preferences.getString("KEY_PHONE_NO", "");

        if (phoneNo.length() < 11) {
            Toast.makeText(context, R.string.toast_invalid_phoneNo, Toast.LENGTH_SHORT).show();
        } else {
            SmsManager.getDefault()
                    .sendTextMessage(phoneNo, null, createItemList(accountant, list, context), null, null);
            Toast.makeText(context, R.string.toast_success_export, Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private static String createItemList(Accountant accountant, List<Item> list, Context context) {
        StringBuilder output = new StringBuilder(context.getString(R.string.text_total_money) + " " + money(accountant.getTotalAllowance()) + "\n\n");

        for (Item i : list)
            output.append(i.getName())
                    .append(" - ")                             //
                    .append("(")
                    .append(String.valueOf(i))
                    .append("x) ")
                    .append(money(i.getPrice()))
                    .append("\n")
                    .append(money(i.getTotalPrice()))
                    .append("\n\n");

        output.append(context.getString(R.string.text_change_left)).append(" ").append(accountant.getChange());
        return output.toString();
    }
}
