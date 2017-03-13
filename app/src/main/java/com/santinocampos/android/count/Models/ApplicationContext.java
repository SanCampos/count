package com.santinocampos.android.count.Models;

import android.app.Application;
import android.content.Context;

/**
 * Created by thedr on 3/13/2017.
 */

public class ApplicationContext extends Application {
    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        if (mContext == null)
            mContext = getApplicationContext();
    }

    public static Context get() {
        return mContext;
    }
}
