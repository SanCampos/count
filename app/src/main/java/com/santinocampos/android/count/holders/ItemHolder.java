package com.santinocampos.android.count.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.R;
import com.santinocampos.android.count.Utils.MoneyUtils;

/**
 * Created by thedr on 12/15/2016.
 */
public class ItemHolder extends RecyclerView.ViewHolder {
    private TextView mItemNameTextView;
    private TextView mItemInitialPriceTextView;
    private TextView mItemTotalPriceTextView;
    private TextView mItemCountTextView;

    /** HACKY V1 OF NEW ITEM VIEW, NOT FOR PRODUCTION **/
    public ItemHolder(View itemView) {
        super(itemView);
        mItemNameTextView = (TextView) itemView.findViewById(R.id.item_name_textView);
        mItemCountTextView = (TextView) itemView.findViewById(R.id.item_count_textView);
        mItemInitialPriceTextView = (TextView) itemView.findViewById(R.id.item_initialPrice_textView);
        mItemTotalPriceTextView = (TextView) itemView.findViewById(R.id.item_totalPrice_textView);
    }

    public void bindItem(Item item, int count) {
        mItemNameTextView.setText(item.getName());
        mItemCountTextView.setText('x' + String.valueOf(count));
        mItemInitialPriceTextView.setText(MoneyUtils.prep(item.getPrice()));
        mItemTotalPriceTextView.setText(MoneyUtils.prep(item.getPrice() * count));
    }
}
