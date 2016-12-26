package com.santinocampos.android.count.Models;


import java.util.UUID;

/**
 * Created by thedr on 11/1/2016.
 */
public class Item {
    private String mName;
    private double mPrice;
    private int mCount;
    private UUID mUUID;

    public Item(String mName, double mPrice, int count) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mCount = count;
        mUUID = UUID.randomUUID();
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


    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        else if (object.getClass() != this.getClass()) return false;

        Item item = (Item) object;

        if (!item.getName().equals(this.getName())) return false;
        else if (item.getPrice() != this.getPrice()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.mName.hashCode();
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.mPrice) ^ (Double.doubleToLongBits(this.mPrice) >>> 32));
        return hash;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void increaseCountBy(int amount) {
        mCount += amount;
    }
}
