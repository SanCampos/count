package com.santinocampos.android.count.Models;

import android.content.Context;

import com.santinocampos.android.count.Database.ItemDbSchema;
import com.santinocampos.android.count.Utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by thedr on 11/1/2016.
 */
public class Accountant {
    private double mTotalMoney;
    private Context mContext;
    private Realm mRealm;
    private static RealmConfiguration mRealmConfiguration;

    private List<Item> mItemList;

    public Accountant(Context context) {
        mTotalMoney = 0;
        mContext = context.getApplicationContext();
        mItemList = new ArrayList<>();

        Realm.init(mContext);
        mRealmConfiguration = new RealmConfiguration.Builder()
                                                    .deleteRealmIfMigrationNeeded()
                                                    .name(ItemDbSchema.NAME).build();
        Realm.setDefaultConfiguration(mRealmConfiguration);
        mRealm = Realm.getDefaultInstance();

        updateItemList();
    }

    public int addItem(final Item latestItem) {
        mRealm.beginTransaction();
        Number firstId = mRealm.where(Item.class).max("ID");
        int nextId = firstId != null ? firstId.intValue() + 1 : 0;
        Item firstItem = mRealm.where(Item.class)
                .equalTo("mName", latestItem.getName())
                .equalTo("mPrice", latestItem.getPrice())
                .findFirst();

        if (firstItem == null) {
            latestItem.setID(nextId);
            mRealm.copyToRealm(latestItem);
        } else  {
            firstItem.setCount(firstItem.getCount() +  latestItem.getCount());
        }
        mRealm.commitTransaction();
        updateItemList();

        int index = mItemList.indexOf(latestItem);
        return index;
    }

    public int removeItem(Item item) {
        int indexOfItem = mItemList.indexOf(item);

        mRealm.beginTransaction();
        RealmResults<Item> items = mRealm.where(Item.class)
                                       .equalTo("ID", item.getID())
                                       .findAll();
        items.deleteAllFromRealm();
        mRealm.commitTransaction();
        updateItemList();

        return indexOfItem;
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
        return NumberUtils.MONEY.prep(mTotalMoney, mContext);
    }

    public List<Item> getItemList() {
        return mItemList;
    }

    public String getChange() {
        double cost = 0;

        for (Item item : mItemList)
            cost += item.getPrice() * item.getCount();

        return NumberUtils.MONEY.prep(mTotalMoney - cost, mContext);
    }

    private void updateItemList() {
        mItemList = mRealm.where(Item.class).findAllSorted("mPrice", Sort.DESCENDING);
    }

    public static RealmConfiguration getRealmConfiguration() {
        if (mRealmConfiguration == null) {
            mRealmConfiguration = new RealmConfiguration.Builder()
                                                        .deleteRealmIfMigrationNeeded()
                                                        .name(ItemDbSchema.NAME)
                                                        .build();
        }
        return mRealmConfiguration;
    }

    public String individualPriceOf(Item i) {
        return NumberUtils.MONEY.prep(i.getPrice(), mContext);
    }

    public String totalPriceOf(Item i) {
       return NumberUtils.MONEY.prep(i.getCount() * i.getPrice(), mContext);
    }

    public String countOf(Item i) {
       return String.valueOf(i.getCount());
    }

    public void clearList() {
        mRealm.beginTransaction();
        mRealm.deleteAll();
        mRealm.commitTransaction();
        updateItemList();
    }
}
