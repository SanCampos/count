package com.santinocampos.android.count.Controllers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by thedr on 5/22/2017.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public boolean supportsPredictiveItemAnimations() {
        return true;
    }
}
