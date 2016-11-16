package com.santinocampos.android.count.Views;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.R;

public class AddItemFragment extends AppCompatDialogFragment {
    private static final String ITEM_COUNT = "itemCount";
    private static final String ITEM_NAME = "itemName";
    private static final String ITEM_PRICE = "itemPrice";

    private EditText mItemNameEditText;
    private EditText mItemPriceEditText;

    private ImageButton mDecreaseCount;
    private ImageButton mIncreaseCount;
    private TextView mItemCountTextView;

    private static final int MIN_ITEM_COUNT = 1;
    private static final int MAX = 9;

    private String mItemName = "";
    private String mItemPrice = "";
    private int mItemCount = MIN_ITEM_COUNT;

    public interface DialogListener {
        void addItem(Item item, int count);
    }

    DialogListener mDialogListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDialogListener = (DialogListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_item, null);

        if (savedInstanceState != null) {
            mItemName = savedInstanceState.getString(ITEM_NAME, "");
            mItemPrice = savedInstanceState.getString(ITEM_PRICE, "");
            mItemCount = savedInstanceState.getInt(ITEM_COUNT, MIN_ITEM_COUNT);
        }

        mItemNameEditText = (EditText) v.findViewById(R.id.input_add_item_name);
        mItemNameEditText.setText(mItemName);
        mItemNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mItemName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mItemPriceEditText = (EditText) v.findViewById(R.id.input_add_item_price);
        mItemPriceEditText.setText(mItemPrice);
        mItemPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mItemPrice = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mItemCountTextView = (TextView) v.findViewById(R.id.item_count);
        mItemCountTextView.setText(String.valueOf(mItemCount));

        mDecreaseCount = (ImageButton) v.findViewById(R.id.btn_decrease_count);
        mDecreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCount(-1);
            }
        });

        mIncreaseCount = (ImageButton) v.findViewById(R.id.btn_increase_count);
        mIncreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCount(1);
            }
        });

        checkToHideButton();

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.label_add_item_title)
                .setNegativeButton(R.string.button_cancel_add_item, null)
                .setPositiveButton(R.string.button_add_item, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button addItem = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                addItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemCount == 0 || mItemName.length() == 0 || Double.parseDouble(mItemPrice) == 0)
                            Toast.makeText(getActivity(), R.string.toast_error_adding_item, Toast.LENGTH_SHORT).show();
                        else {
                            mDialogListener.addItem(new Item(mItemName, Double.parseDouble(mItemPrice)), mItemCount);
                            dismiss();
                        }
                    }
                });
            }
        });
        return dialog;
    }

    private void changeCount(int i) {
        int changedCount = mItemCount + i;
        mItemCount = changedCount >= MIN_ITEM_COUNT && changedCount <= MAX ? changedCount : mItemCount;
        mItemCountTextView.setText(String.valueOf(mItemCount));
        checkToHideButton();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(ITEM_COUNT, mItemCount);
        savedInstanceState.putString(ITEM_PRICE, mItemPrice);
        savedInstanceState.putString(ITEM_NAME, mItemName);
    }

    private void checkToHideButton() {
        if (mItemCount == MAX) mIncreaseCount.setVisibility(View.INVISIBLE);
        else if (mItemCount == MIN_ITEM_COUNT) mDecreaseCount.setVisibility(View.INVISIBLE);
        else {
            mIncreaseCount.setVisibility(View.VISIBLE);
            mDecreaseCount.setVisibility(View.VISIBLE);
        }
    }

}