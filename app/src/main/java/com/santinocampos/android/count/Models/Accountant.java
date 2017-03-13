package com.santinocampos.android.count.Models;

import android.content.Context;

import com.santinocampos.android.count.Database.ItemDbSchema;
import com.santinocampos.android.count.Utils.MoneyUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.ItemRealmProxy;
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

    private List<Item> mItemList;

    public Accountant() {
        mTotalMoney = 0;
        mContext = ApplicationContext.get();
        mItemList = new ArrayList<>();

        Realm.init(mContext);
        RealmConfiguration mRealmConfiguration = new RealmConfiguration.Builder()
                                                                       .deleteRealmIfMigrationNeeded()
                                                                       .name(ItemDbSchema.NAME).build();
        Realm.setDefaultConfiguration(mRealmConfiguration);
        mRealm = Realm.getDefaultInstance();

        updateItemList();
    }

    public void addItem(final Item latestItem) {
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
    }

    public void removeItem(String itemName, double itemPrice) {
        mRealm.beginTransaction();
        RealmResults<Item> items = mRealm.where(Item.class)
                                       .equalTo("mName", itemName)
                                       .equalTo("mPrice", itemPrice)
                                       .findAll();
        items.deleteAllFromRealm();
        updateItemList();
        mRealm.commitTransaction();
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
         mItemList = mRealm.where(Item.class).findAllSorted("mPrice", Sort.DESCENDING);
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
        mRealm.beginTransaction();
        mRealm.deleteAll();
        mRealm.commitTransaction();
        updateItemList();
    }
}
