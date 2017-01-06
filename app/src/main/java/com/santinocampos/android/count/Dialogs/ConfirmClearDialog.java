package com.santinocampos.android.count.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.santinocampos.android.count.R;

/**
 * Created by thedr on 12/19/2016.
 */
public class ConfirmClearDialog extends AbstractDialog {

    private static final String TAG = "ConfirmClearDialog";

    public ConfirmClearDialog() {
        super(TAG);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                              .setTitle(R.string.title_confirm_clear)
                              .setMessage(R.string.message_confirm_clear)
                              .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {
                                      mDialogListener.clearList();
                                  }
                              })
                              .setNegativeButton(android.R.string.no, null)
                              .create();
    }
}
