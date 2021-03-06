package com.santinocampos.android.count.Dialogs;

import android.app.Activity;
import android.support.v7.app.AppCompatDialogFragment;

import com.santinocampos.android.count.Listeners.DialogListener;

/**
 * Created by thedr on 1/6/2017.
 */

public abstract class AbstractDialog extends AppCompatDialogFragment {

    protected DialogListener mDialogListener;

    protected String TAG;

    public AbstractDialog() {
        super();
    }

    public String getTagString() {
        return TAG;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDialogListener = (DialogListener) activity;
    }
}
