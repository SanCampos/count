package com.santinocampos.android.count.Utils;

import java.text.DecimalFormat;

/**
 * Created by thedr on 12/4/2016.
 */
public class DecUtils {

    public static String clean(double money) {
        StringBuilder decimalFormat = new StringBuilder("#,###");
        if (money % 1 != 0) decimalFormat.append(".00");

        return new DecimalFormat(decimalFormat.toString()).format(money);
    }
}
