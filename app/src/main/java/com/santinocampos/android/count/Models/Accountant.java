package com.santinocampos.android.count.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.santinocampos.android.count.Database.ItemBaseHelper;
import com.santinocampos.android.count.Database.ItemDbSchema.ItemTable;
import com.santinocampos.android.count.Utils.MoneyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thedr on 11/1/2016.
 */
public class Accountant {

    private static Accountant sAccountant;

    private double mTotalMoney;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Accountant get(Context context) {
        sAccountant = sAccountant == null ? new Accountant(context) : sAccountant;
        return sAccountant;
    }

    private Accountant(Context context) {
        mTotalMoney = 0;
        mContext = context.getApplicationContext();
        mDatabase = new ItemBaseHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Item i, int count) {
        ContentValues values = new ContentValues();
        values.put(ItemTable.cols.NAME, i.getName());
        values.put(ItemTable.cols.PRICE, i.getPrice());
        values.put(ItemTable.cols.COUNT, count);

        return values;
    }
    public void addItem(Item item, int count) {
        ContentValues values = getContentValues(item, count);

        mDatabase.insert(ItemTable.NAME, null, values);
    }

    public void removeItem(int i) {

    }

    public void addMoney(double money, boolean isSet) {
        mTotalMoney += isSet ? money - mTotalMoney : money;
    }

    public String getTotalMoney() {
        return MoneyUtils.prep(mTotalMoney);
    }

    public String getChange() {
        double cost = 0;

        for (Item item : new ArrayList<Item>())
            cost += totalPriceOf(item);

        return MoneyUtils.prep(mTotalMoney - cost);
    }

     public List<Item> getItemList() {
        return new ArrayList<>();
     }

    public double individualPriceOf(Item i) {
        return i.getPrice();
    }

    public double totalPriceOf(Item i) {
       return 0;
    }

    public int countOf(Item i) {
       return 0;
    }

    public void clearList() {

    }
}
