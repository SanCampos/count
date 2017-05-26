package com.santinocampos.android.count.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.santinocampos.android.count.Listeners.DialogListener;
import com.santinocampos.android.count.R;

/**
 * Created by thedr on 5/23/2017.
 */

public class MoneyInfoDialog extends AbstractDialog {

    private static final String tagString = "MoneyInfoDialog";

    private TextView mAmountView;
    private TextView mBalanceView;

    private DialogListener mDialogListener;

    public MoneyInfoDialog() {
        super();
        this.TAG = tagString;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDialogListener = ((DialogListener) activity);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                              .setTitle(R.string.money_info_title)
                              .setView(onCreateView())
                              .create();
    }

    private View onCreateView() {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.money_dialog, null);

        mAmountView = ((TextView) v.findViewById(R.id.money_amt));
        mBalanceView = ((TextView) v.findViewById(R.id.balance_amt));

        return v;
    }
}
