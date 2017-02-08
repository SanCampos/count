package com.santinocampos.android.count.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.santinocampos.android.count.R;

/**
 * Created by thedr on 2/8/2017.
 */

public class ConfirmClearMoneyDialog extends AbstractDialog {

    private static final String TAG = "ConfirmClearMoneyDialog";

    public ConfirmClearMoneyDialog() {
        super(TAG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext()).setTitle(R.string.title_confirm_clear_money)
                                        .setMessage(R.string.messsage_confirm_clear_money)
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mDialogListener.clearMoney();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .create();
    }
}
