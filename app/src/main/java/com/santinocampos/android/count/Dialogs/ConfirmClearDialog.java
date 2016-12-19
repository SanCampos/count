package com.santinocampos.android.count.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import com.santinocampos.android.count.Listeners.DialogListener;
import com.santinocampos.android.count.R;

/**
 * Created by thedr on 12/19/2016.
 */
public class ConfirmClearDialog extends AppCompatDialogFragment {

    private DialogListener mDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDialogListener = (DialogListener) context;
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
