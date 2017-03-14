package com.santinocampos.android.count.Models;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by thedr on 11/1/2016.
 */
public class Item extends RealmObject{
    @PrimaryKey
    private int ID;

    private String mName;
    private double mPrice;
    private int mCount;
    private int mItemType;

    public Item() {/** Required due to Realm **/ }

    public Item(String mName, double mPrice, int count, int itemType) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mCount = count;
        this.mItemType = itemType;
        this.ID = 0;
    }

     /**public Item(String mName, double mPrice, int count) {
        this(mName, mPrice, count, ItemType.NO_TYPE);
    } **/

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

    public void setCount(int count) {
        mCount = count;
    }

    public int getItemTypeInt() {
        return mItemType;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    /** public ItemType getItemTypeInt() {
        return mItemType;
    }  **/

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Item)) return false;
        Item item = ((Item) object);

        return item.getID() == ID;
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
