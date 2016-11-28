package com.santinocampos.android.count.Dialogs;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.santinocampos.android.count.Listeners.DialogListener;
import com.santinocampos.android.count.R;

/**
 * Created by thedr on 11/16/2016.
 */
public class AddMoneyDialog extends AppCompatDialogFragment {

    private DialogListener mDialogListener;
    private EditText mAddMoneyEditText;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDialogListener = (DialogListener) activity;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       return new AlertDialog.Builder(getActivity())
                        .setView(createView(savedInstanceState))
                        .setTitle(R.string.label_dialog_addMoney)
                        .setPositiveButton(R.string.btn_addMoney_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDialogListener.addMoney(Double.parseDouble(mAddMoneyEditText.getText().toString()));
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        })
                        .create();
    }

    private View createView(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_money, null);
        mAddMoneyEditText = (EditText) v.findViewById(R.id.editText_addMoney);
        return v;
    }
}
