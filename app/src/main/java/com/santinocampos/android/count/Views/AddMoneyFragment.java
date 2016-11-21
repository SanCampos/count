package com.santinocampos.android.count.Views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.santinocampos.android.count.Listeners.DialogListener;
import com.santinocampos.android.count.R;

/**
 * Created by thedr on 11/16/2016.
 */
public class AddMoneyFragment extends DialogFragment {

    private DialogListener mDialogListener;
    private EditText mAddMoneyEditText;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDialogListener = (DialogListener) activity;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_money);
    }
}
