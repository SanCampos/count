package com.santinocampos.android.count.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.santinocampos.android.count.R;

/**
 * Created by thedr on 12/16/2016.
 */
public class ConfirmExportDialog extends AbstractDialog {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                              .setTitle(R.string.title_confirm_export)
                              .setMessage(R.string.message_confirm_export)
                              .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {
                                      mDialogListener.exportList();
                                  }
                              })
                              .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {
                                      dismiss();
                                  }
                              })
                              .create();
    }
}
