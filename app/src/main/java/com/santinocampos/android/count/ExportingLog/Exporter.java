package com.santinocampos.android.count.ExportingLog;

import android.content.Context;
import android.content.Intent;

/**
 * Created by thedr on 12/15/2016.
 */
public class Exporter {

    private static final String EXTRA_PROTOCOL_VERSION = "com.facebook.orca.extra.PROTOCOL_VERSION";
    private static final String EXTRA_APP_ID = "com.facebook.orca.extra.APPLICATION_ID";
    private static final int PROTOCOL_VERSION = 20150314;
    private static final String YOUR_APP_ID = "142011586285179]";
    private static final int SHARE_TO_MESSENGER_REQUEST_CODE = 1;

    public Exporter(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setPackage("com.facebook.orca");

    }
}
