package com.santinocampos.android.count.ListSending;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.Models.Item.Entry;
import com.santinocampos.android.count.R;

import java.util.List;

import static com.santinocampos.android.count.Utils.NumberUtils.MONEY.money;

/**
 * Created by thedr on 12/15/2016.
 */
public class ListSender {

    public static void exportItemList(Accountant accountant, Context context) {
        List<Entry> list = accountant.getEntryList();
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
    private static String createItemList(Accountant accountant, List<Entry> list, Context context) {
        StringBuilder output = new StringBuilder(context.getString(R.string.text_total_money) + " " + money(accountant.getTotalMoney()) + "\n\n");

        for (Entry e : list)
            output.append(e.getName())
                    .append(" - ")                             //
                    .append("(")
                    .append(String.valueOf(e))
                    .append("x) ")
                    .append(money(e.getPrice()))
                    .append("\n")
                    .append(money(e.getTotalPrice()))
                    .append("\n\n");

        output.append(context.getString(R.string.text_change_left)).append(" ").append(accountant.getChange());
        return output.toString();
    }
}
