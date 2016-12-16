package com.santinocampos.android.count.Models;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by thedr on 11/1/2016.
 */
public class Accountant {

    private static Accountant sAccountant;

    private double mTotalMoney;
    private Map<Item, Integer> mItemList;

    public static Accountant get(Context context) {
        sAccountant = sAccountant == null ? new Accountant(context) : sAccountant;
        return sAccountant;
    }

    private Accountant(Context context) {
        mTotalMoney = 0;
        mItemList = new LinkedHashMap<>();

        /** Item generation code - DO NOT PUT IN PRODUCTION
        for (int i = 0; i < 100; i++)
            addItem(new Item("test", new Random().nextInt(43)), 4); **/
    }

    public void addItem(Item item, int count) {
        int updatedCount = mItemList.containsKey(item) ? mItemList.get(item) + count : count;
        mItemList.put(item, updatedCount);
    }

    public void addMoney(double money, boolean isSet) {
        mTotalMoney += isSet ? money - mTotalMoney : money;
    }

    public double getTotalMoney() {
        return mTotalMoney;
    }

    public double getChange() {
        double cost = 0;

        for (Item item : mItemList.keySet())
            cost += item.getPrice() * mItemList.get(item);

        return mTotalMoney - cost;
    }

    public String getList() {
        StringBuilder output = new StringBuilder("");

        for (Item i : getItemList().keySet())
            output.append(i.getName())
                  .append(" - ")
                  .append('(' + mItemList.get(i) + ')')
                  .append(i.getPrice() + "\n")
                  .append(totalPriceOf(i));

        return output.toString();
    }

    public Map<Item, Integer> getItemList() {
        return mItemList;
    }

    public double totalPriceOf(Item i) {
        return i.getPrice() * mItemList.get(i);
    }

    public int getCountOf(Item i) {
        return mItemList.get(i);
    }
}
