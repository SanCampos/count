package com.santinocampos.android.count.Models;

import android.content.Context;

import com.santinocampos.android.count.Utils.MoneyUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by thedr on 11/1/2016.
 */
public class Accountant {

    private static Accountant sAccountant;

    private double mTotalMoney;
    private Map<Item, Integer> mItemMap;
    private List<Item> mItemList;

    public static Accountant get(Context context) {
        sAccountant = sAccountant == null ? new Accountant(context) : sAccountant;
        return sAccountant;
    }

    private Accountant(Context context) {
        mTotalMoney = 0;
        mItemMap = new LinkedHashMap<>();
        mItemList = new ArrayList<>();


        for (int i = 0; i < 100; i++)
            addItem(new Item("test", new Random().nextInt(43)), 4);
    }

    public void addItem(Item item, int count) {
        int updatedCount = mItemMap.containsKey(item) ? mItemMap.get(item) + count : count;
        mItemMap.put(item, updatedCount);
        mItemList.add(item);
    }

    public void removeItem(int i) {
        mItemMap.remove(mItemList.get(i));
        mItemList.remove(i);
    }

    public void addMoney(double money, boolean isSet) {
        mTotalMoney += isSet ? money - mTotalMoney : money;
    }

    public String getTotalMoney() {
        return MoneyUtils.prep(mTotalMoney);
    }

    public String getChange() {
        double cost = 0;

        for (Item item : mItemMap.keySet())
            cost += item.getPrice() * mItemMap.get(item);

        return MoneyUtils.prep(mTotalMoney - cost);
    }

    public String getList() {
        StringBuilder output = new StringBuilder("");

        for (Item i : getItemList())
            output.append(i.getName())
                  .append(" - ")
                  .append("(" + mItemMap.get(i) + "x) ")
                  .append(individualPriceOf(i) + "\n")
                  .append(totalPriceOf(i) + "\n\n");

        return output.toString();
    }

     public List<Item> getItemList() {
        return mItemList;
     }

    public String individualPriceOf(Item i) {
        return MoneyUtils.prep(i.getPrice());
    }

    public String totalPriceOf(Item i) {
        return MoneyUtils.prep(i.getPrice() * mItemMap.get(i));
    }

    public String countOf(Item i) {
        return String.valueOf(mItemMap.get(i));
    }
}
