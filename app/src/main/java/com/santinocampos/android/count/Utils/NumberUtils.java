package com.santinocampos.android.count.Utils;

import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.santinocampos.android.count.Models.Currency;

import java.text.DecimalFormat;

/**
 * Created by thedr on 12/7/2016.
 */
public class NumberUtils {

    public static class MONEY {

        private static char cSymbol;
        
        public static String money(double money) {
            return DECIMAL.clean(money) + cSymbol;
        }

        public static void setCurrentCurrencySymbol(Context context) {
            int currencyIndex = PreferenceManager.getDefaultSharedPreferences(context).getInt("KEY_CURRENCY", 0);
            cSymbol = Currency.values()[currencyIndex].getSymbol();
        }
    }

    private static class DECIMAL {

        private static String clean(double money) {
            StringBuilder decimalFormat = new StringBuilder("#,###");
            if (money % 1 != 0) decimalFormat.append(".00");

            return new DecimalFormat(decimalFormat.toString()).format(money);
        }
    }
}



