package com.santinocampos.android.count.Models;


import com.santinocampos.android.count.ItemType.ItemType;

import io.realm.RealmObject;

/**
 * Created by thedr on 11/1/2016.
 */
public class Item extends RealmObject{
    private String mName;
    private double mPrice;
    private int mCount;
    private int itemType;

    public Item(String mName, double mPrice, int count, int itemType) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mCount = count;
    }

     /**public Item(String mName, double mPrice, int count) {
        this(mName, mPrice, count, ItemType.NO_TYPE);
    } **/

    public Item() {

    }
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getPrice() {
        return mPrice;
    }

    public int getCount() {
        return mCount;
    }

    /** public ItemType getItemType() {
        return mItemType;
    }  **/

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        else if (object.getClass() != this.getClass()) return false;

        Item item = (Item) object;

        if (!item.getName().equals(this.getName())) return false;
        else if (item.getPrice() != this.getPrice()) return false;
        //else if (!item.getItemType().equals(this.getItemType())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.mName.hashCode();
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.mPrice) ^ (Double.doubleToLongBits(this.mPrice) >>> 32));
        return hash;
    }

    public void increaseCountBy(int amount) {
        mCount += amount;
    }
}
