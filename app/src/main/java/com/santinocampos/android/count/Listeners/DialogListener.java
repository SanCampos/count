package com.santinocampos.android.count.Listeners;

import com.santinocampos.android.count.Models.Item;

/**
 * Created by thedr on 11/16/2016.
 */
public interface DialogListener {
    void addMoney(double money);
    void addItem(Item item, int count);
}