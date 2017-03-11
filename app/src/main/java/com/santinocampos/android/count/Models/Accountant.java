package com.santinocampos.android.count.Models;

import android.content.ContentValues;
import android.content.Context;

import com.santinocampos.android.count.Database.ItemDbSchema.ItemTable;
import com.santinocampos.android.count.Utils.MoneyUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by thedr on 11/1/2016.
 */
public class Accountant {

    private static Accountant sAccountant;

    private double mTotalMoney;
    private Context mContext;

    private List<Item> mItemList;

    public Accountant get(Context context) {
        RealmConfiguration mRealmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(mRealmConfiguration);
    }

    private Accountant(Context context) {
        mTotalMoney = 0;
        mContext = context.getApplicationContext();
        mItemList = new ArrayList<>();
        updateItemList();
    }

    private static ContentValues getContentValues(Item i) {
        ContentValues values = new ContentValues();
        values.put(ItemTable.cols.NAME, i.getName());
        values.put(ItemTable.cols.PRICE, i.getPrice());
        values.put(ItemTable.cols.COUNT, i.getCount());
        values.put(ItemTable.cols.TOTAL_PRICE, i.getPrice() * i.getCount());
        values.put(ItemTable.cols.ITEM_TYPE, i.getItemType().getItemTypeNameID());
        return values;
    }

    public void addItem(Item latestItem) {
        updateItemList();
    }

    public void removeItem(String itemName, String itemPrice) {
        updateItemList();
    }

    public void addMoney(double money, boolean isSet) {
        mTotalMoney += isSet ? money - mTotalMoney : money;
    }

    public void clearMoney() {
         addMoney(0, true);
    }

    public double getTotalMoney() {
        return mTotalMoney;
    }

    public String getTotalMoneyInformation() {
        return MoneyUtils.prep(mTotalMoney, mContext);
    }

    public List<Item> getItemList() {
        return mItemList;
    }

    public String getChange() {
        double cost = 0;

        for (Item item : mItemList)
            cost += item.getPrice() * item.getCount();

        return MoneyUtils.prep(mTotalMoney - cost, mContext);
    }

     private void updateItemList() {
         mItemList.clear();
     }

    public String individualPriceOf(Item i) {
        return MoneyUtils.prep(i.getPrice(), mContext);
    }

    public String totalPriceOf(Item i) {
       return MoneyUtils.prep(i.getCount() * i.getPrice(), mContext);
    }

    public String countOf(Item i) {
       return String.valueOf(i.getCount());
    }

    public void clearList() {
        updateItemList();
    }
}
