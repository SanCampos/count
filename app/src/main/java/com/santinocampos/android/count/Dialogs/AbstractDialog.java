package com.santinocampos.android.count.Dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;

import com.santinocampos.android.count.Listeners.DialogListener;

/**
 * Created by thedr on 1/6/2017.
 */

public abstract class AbstractDialog extends AppCompatDialogFragment {

    protected DialogListener mDialogListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDialogListener = (DialogListener) activity;
    }
}
