package com.santinocampos.android.count.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.santinocampos.android.count.ItemType.ItemTypeAdapter;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.ItemType.ItemType;
import com.santinocampos.android.count.R;

public class AddItemDialog extends AbstractDialog {
    private static final String ITEM_COUNT = "itemCount";
    private static final String ITEM_NAME = "itemName";
    private static final String ITEM_PRICE = "itemPrice";

    private static final String TAG = "AddItemDialog";

    private Spinner mSpinner;

    private EditText mItemNameEditText;
    private EditText mItemPriceEditText;

    private ImageButton mDecreaseCount;
    private ImageButton mIncreaseCount;
    private TextView mItemCountTextView;

    private static final int MIN_ITEM_COUNT = 1;
    private static final int MAX_ITEM_COUNT = 9;

    public AddItemDialog() {
        super(TAG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setView(onCreateView(savedInstanceState))
                            .setTitle(R.string.title_add_item)
                            .setNegativeButton(android.R.string.cancel, null)
                            .setPositiveButton(R.string.button_add_item, null)
                            .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button addItem = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                addItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int itemCount = Integer.parseInt(mItemCountTextView.getText().toString());
                        String itemPrice = mItemPriceEditText.getText().toString();
                        String itemName = mItemNameEditText.getText().toString();
                        if (itemCount == 0 || itemName.length() == 0 || itemPrice.length() == 0)
                            Toast.makeText(getActivity(), R.string.toast_error_adding_item, Toast.LENGTH_SHORT).show();
                        else {
                            mDialogListener.addItem(new Item(itemName,
                                                    Double.parseDouble(itemPrice),
                                                    itemCount, (ItemType) mSpinner.getSelectedItem()));
                            dismiss();
                        }
                    }
                });
            }
        });
        return dialog;
    }

    private View onCreateView(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_item, null);

        mItemNameEditText = (EditText) v.findViewById(R.id.input_add_item_name);
        mItemPriceEditText = (EditText) v.findViewById(R.id.input_add_item_price);
        mItemCountTextView = (TextView) v.findViewById(R.id.item_count);

        if (savedInstanceState != null) {
            mItemNameEditText.setText(savedInstanceState.getString(ITEM_NAME, ""));
            mItemPriceEditText.setText(savedInstanceState.getString(ITEM_PRICE, ""));
            mItemCountTextView.setText(String.valueOf(savedInstanceState.getInt(ITEM_COUNT, MIN_ITEM_COUNT)));
        } else mItemCountTextView.setText(String.valueOf(MIN_ITEM_COUNT));

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

        mSpinner = ((Spinner) v.findViewById(R.id.spinner_types));

        ItemTypeAdapter itemTypeAdapter = new ItemTypeAdapter(getContext(),
                R.layout.support_simple_spinner_dropdown_item, ItemType.values());
        itemTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        mSpinner.setAdapter(itemTypeAdapter);

        checkToHideButton();
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(ITEM_NAME, mItemNameEditText.getText().toString());
        savedInstanceState.putString(ITEM_PRICE, mItemPriceEditText.getText().toString());
        savedInstanceState.putInt(ITEM_COUNT, Integer.parseInt(mItemCountTextView.getText().toString()));

    }

    private void checkToHideButton() {
        //Hides respective buttons if item count reaches limit
        if (Integer.parseInt(mItemCountTextView.getText().toString()) == MAX_ITEM_COUNT)
            mIncreaseCount.setVisibility(View.INVISIBLE);
        else if (Integer.parseInt(mItemCountTextView.getText().toString()) == MIN_ITEM_COUNT)
            mDecreaseCount.setVisibility(View.INVISIBLE);

        //Sets visibility of invisible buttons if not touching limit
        else if (mIncreaseCount.getVisibility() == View.INVISIBLE) mIncreaseCount.setVisibility(View.VISIBLE);
        else if (mDecreaseCount.getVisibility() == View.INVISIBLE) mDecreaseCount.setVisibility(View.VISIBLE);
    }

    private void changeCount(int i) {
        if (Integer.parseInt(mItemCountTextView.getText().toString()) + i <= MAX_ITEM_COUNT &&
            Integer.parseInt(mItemCountTextView.getText().toString()) + i >= MIN_ITEM_COUNT)
            mItemCountTextView.setText(String.valueOf(Integer.parseInt(mItemCountTextView.getText().toString()) + i));
        checkToHideButton();
    }
}
