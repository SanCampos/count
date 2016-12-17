package com.santinocampos.android.count.Models;

import android.content.Context;

import com.santinocampos.android.count.Utils.MoneyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

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


        for (int i = 0; i < 100; i++)
            addItem(new Item("test", new Random().nextInt(43)), 4);
    }

    public void addItem(Item item, int count) {
        int updatedCount = mItemList.containsKey(item) ? mItemList.get(item) + count : count;
        mItemList.put(item, updatedCount);
    }

    public void removeItem(int i) {
        List<Item> keys = new ArrayList(mItemList.keySet());
        mItemList.remove(keys.get(i));
    }

    public void addMoney(double money, boolean isSet) {
        mTotalMoney += isSet ? money - mTotalMoney : money;
    }

    public String getTotalMoney() {
        return MoneyUtils.prep(mTotalMoney);
    }

    public String getChange() {
        double cost = 0;

        for (Item item : mItemList.keySet())
            cost += item.getPrice() * mItemList.get(item);

        return MoneyUtils.prep(mTotalMoney - cost);
    }

    public String getList() {
        StringBuilder output = new StringBuilder("");

        for (Item i : getItemList().keySet())
            output.append(i.getName())
                  .append(" - ")
                  .append("(" + mItemList.get(i) + "x) ")
                  .append(individualPriceOf(i) + "\n")
                  .append(totalPriceOf(i) + "\n\n");

        return output.toString();
    }

     public Map<Item, Integer> getItemList() {
        return mItemList;
    }

    public String individualPriceOf(Item i) {
        return MoneyUtils.prep(i.getPrice());
    }

    public String totalPriceOf(Item i) {
        return MoneyUtils.prep(i.getPrice() * mItemList.get(i));
    }

    public String countOf(Item i) {
        return String.valueOf(mItemList.get(i));
    }
}
