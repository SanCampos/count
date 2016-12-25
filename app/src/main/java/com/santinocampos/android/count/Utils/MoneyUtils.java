package com.santinocampos.android.count.Utils;

/**
 * Created by thedr on 12/7/2016.
 */
public class MoneyUtils {

    public static char PESO = 'P';
    public static char DOLLAR = '$';

    public static char CURRENT_CURRENCY = PESO;

    public static String prep(double money) {
        return DecUtils.clean(money) + CURRENT_CURRENCY;
    }

}
