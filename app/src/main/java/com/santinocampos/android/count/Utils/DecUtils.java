package com.santinocampos.android.count.Utils;

import java.text.DecimalFormat;

/**
 * Created by thedr on 12/4/2016.
 */
public class DecUtils {

    public static String clean(double money) {
        return money % 1 == 0 ? String.valueOf(Math.round(money)) : new DecimalFormat("0.00").format(money);
    }
}
