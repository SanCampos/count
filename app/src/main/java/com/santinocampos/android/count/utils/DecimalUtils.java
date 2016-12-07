package com.santinocampos.android.count.utils;

/**
 * Created by thedr on 12/4/2016.
 */
public class DecimalUtils {

    public static String clean(double money) {
        return money % 1 == 0 ? String.valueOf(Math.round(money)) : String.valueOf(money);
    }
}
