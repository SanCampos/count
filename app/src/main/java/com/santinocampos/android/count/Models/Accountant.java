package com.santinocampos.android.count.Models;

import android.content.Context;

import com.santinocampos.android.count.Models.Item.Entry;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.santinocampos.android.count.Utils.NumberUtils.MONEY.money;

/**
 * Created by thedr on 11/1/2016.
 */
public class Accountant {
    private double mTotalMoney;
    private static Context mContext;
    private Realm mRealm;
    private static RealmConfiguration mRealmConfiguration;

    private List<Entry> mEntryList;

    public Accountant(Context context) {
        mTotalMoney = 0;
        mContext = context.getApplicationContext();
        mEntryList = new ArrayList<>();

        Realm.init(mContext);
        mRealmConfiguration = getRealmConfiguration();
        Realm.setDefaultConfiguration(mRealmConfiguration);
        mRealm = Realm.getDefaultInstance();

        updateItemList();
       }

    public void addItem(final Entry latestEntry) {
        mRealm.beginTransaction();
        Number latestID = mRealm.where(Entry.class).max("ID");
        int nextId = latestID != null ? latestID.intValue() + 1 : 0;
        Entry mFirstEntry = mRealm.where(Entry.class)
                               .equalTo("mName", latestEntry.getName())
                               .equalTo("mPrice", latestEntry.getPrice())
                               .findFirst();
        if (mFirstEntry == null) {
            latestEntry.setID(nextId);
            mRealm.copyToRealm(latestEntry);
        } else {
            mFirstEntry.updateCount(latestEntry.getCount());
        }
        mRealm.commitTransaction();
        updateItemList();
    }

    public void removeItem(int ID) {
        mRealm.beginTransaction();
        RealmResults<Entry> mEntries = mRealm.where(Entry.class)
                                       .equalTo("ID", ID)
                                       .findAll();
        mEntries.deleteAllFromRealm();
        mRealm.commitTransaction();
        updateItemList();
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

    public List<Entry> getEntryList() {
        return mEntryList;
    }

    public String getChange() {
        double cost = 0;

        for (Entry mEntry : mEntryList)
            cost += mEntry.getTotalPrice();

        return money(mTotalMoney - cost);
    }

    private void updateItemList() {
        mEntryList = mRealm.where(Entry.class).findAllSorted("mTotalPrice", Sort.DESCENDING);
    }

    public static RealmConfiguration  getRealmConfiguration() {
        if (mRealmConfiguration == null) {
            mRealmConfiguration = new RealmConfiguration.Builder()
                                                        .deleteRealmIfMigrationNeeded()
                                                        .name("itemDB")
                                                        .build();
        }
        return mRealmConfiguration;
    }

    public void clearList() {
        mRealm.beginTransaction();
        mRealm.deleteAll();
        mRealm.commitTransaction();
        updateItemList();
    }
}
