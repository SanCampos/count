package com.santinocampos.android.count.Listeners;

import com.santinocampos.android.count.Models.Item.Entry;

/**
 * Created by thedr on 11/16/2016.
 */
public interface DialogListener {
    void addMoney(double money, boolean isSet);
    void addItem(Entry entry);
    void exportList();
    void clearList();
    void clearMoney();
    //THIS IS FISHY
}
