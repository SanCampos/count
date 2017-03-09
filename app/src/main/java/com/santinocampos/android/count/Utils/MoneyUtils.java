package com.santinocampos.android.count.Utils;

import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.santinocampos.android.count.Models.Currency;

/**
 * Created by thedr on 12/7/2016.
 */
public class MoneyUtils {

    private static char PESO = 'P';
    private static char DOLLAR = '$';

    private static char CURRENT_CURRENCY = PESO;

    public static String prep(double money, Context context) {
        return DecUtils.clean(money) + getCurrentCurrencySymbol(context);
    }

    private static char getCurrentCurrencySymbol(Context context) {
        int currencyIndex = PreferenceManager.getDefaultSharedPreferences(context).getInt("KEY_CURRENCY", 0);
        return Currency.values()[currencyIndex].getSymbol();
    }
}
