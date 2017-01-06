package com.santinocampos.android.count.Dialogs;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.santinocampos.android.count.R;

/**
 * Created by thedr on 11/16/2016.
 */
public class AddMoneyDialog extends AbstractDialog {

    private static final String TAG = "AddMoneyDialog";

    private EditText mAddMoneyEditText;
    private CheckBox mCheckBox;

    public AddMoneyDialog(){
        super(TAG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       return new AlertDialog.Builder(getActivity())
                        .setView(onCreateView(savedInstanceState))
                        .setTitle(R.string.string_add_money)
                        .setPositiveButton(R.string.btn_addMoney_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDialogListener.addMoney(Double.parseDouble(mAddMoneyEditText.getText().toString()), mCheckBox.isChecked());
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

    private View onCreateView(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_money, null);
        mAddMoneyEditText = (EditText) v.findViewById(R.id.editText_addMoney);
        mCheckBox = (CheckBox) v.findViewById(R.id.checkBox_AddMoney);
        return v;
    }
}
