package com.santinocampos.android.count.Models.Item;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by thedr on 11/1/2016.
 */
public class Entry extends RealmObject{
    @PrimaryKey
    private int ID;

    private String mName;
    private double mPrice;
    private int mCount;
    private int mItemType;

    public Entry() {/** Required due to Realm **/ }

    public Entry(String mName, double mPrice, int count, int itemType) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mCount = count;
        this.mItemType = itemType;
    }

     /**public Entry(String mName, double mPrice, int count) {
        this(mName, mPrice, count, EntryType.NO_TYPE);
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

    public double getTotalPrice() {
        return getPrice() * getCount();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    /** public EntryType getItemTypeInt() {
        return mItemType;
    }  **/

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Entry)) return false;
        Entry mEntry = ((Entry) object);

        return mEntry.getID() == ID;
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
