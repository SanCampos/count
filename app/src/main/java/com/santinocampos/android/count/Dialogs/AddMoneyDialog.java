package com.santinocampos.android.count.Dialogs;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.santinocampos.android.count.R;

/**
 * Created by thedr on 11/16/2016.
 */
public class AddMoneyDialog extends AbstractDialog {

    private static final String tagString = "AddMoneyDialog";

    private EditText mAddMoneyEditText;
    private CheckBox mCheckBox;

    public AddMoneyDialog(){
        super();
        this.TAG = tagString;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       AlertDialog dialog = new AlertDialog.Builder(getActivity())
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

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button addMoneyButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                addMoneyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String money = mAddMoneyEditText.getText().toString();

                        if (money.length() == 0)
                            Toast.makeText(getContext(), R.string.toast_error_adding_money, Toast.LENGTH_LONG).show();
                        else {
                            mDialogListener.addMoney(Double.parseDouble(money), mCheckBox.isChecked());
                            dismiss();
                        }
                    }
                });
            }
        });

        return dialog;
    }

    private View onCreateView(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_money, null);
        mAddMoneyEditText = (EditText) v.findViewById(R.id.editText_addMoney);
        mCheckBox = (CheckBox) v.findViewById(R.id.checkBox_AddMoney);
        return v;
    }
}
