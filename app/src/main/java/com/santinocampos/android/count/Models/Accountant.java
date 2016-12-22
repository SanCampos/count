package com.santinocampos.android.count.Models;

import android.content.Context;

import com.santinocampos.android.count.Utils.MoneyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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


        if (!mItemList.contains(item)) mItemList.add(item);

        Collections.sort(mItemList, new Comparator<Item>() {
            @Override
            public int compare(Item lhs, Item rhs) {
                return (int) (totalPriceOf(rhs) - totalPriceOf(lhs));
            }
        });
    }

    public void removeItem(int i) {
        mItemMap.remove(mItemList.get(i));
        mItemList.remove(i);
    }

    public void changeCountOfBy(Item i, int amount) {
        int changed = mItemMap.get(i) + amount;
        if (changed != 0) mItemMap.put(i, changed);
    }

    public void addMoney(double money, boolean isSet) {
        mTotalMoney += isSet ? money - mTotalMoney : money;
    }

    public String getTotalMoney() {
        return MoneyUtils.prep(mTotalMoney);
    }

    public String getChange() {
        double cost = 0;

        for (Item item : mItemList)
            cost += totalPriceOf(item);

        return MoneyUtils.prep(mTotalMoney - cost);
    }

     public List<Item> getItemList() {
        return mItemList;
     }

    public double individualPriceOf(Item i) {
        return i.getPrice();
    }

    public double totalPriceOf(Item i) {
        return i.getPrice() * mItemMap.get(i);
    }

    public int countOf(Item i) {
        return mItemMap.get(i);
    }

    public void clearList() {
         mItemList.clear();
    }
}
