package com.santinocampos.android.count.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.santinocampos.android.count.Database.ItemBaseHelper;
import com.santinocampos.android.count.Database.ItemCursorWrapper;
import com.santinocampos.android.count.Database.ItemDbSchema.ItemTable;
import com.santinocampos.android.count.Utils.MoneyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thedr on 11/1/2016.
 */
public class Accountant {

    private static Accountant sAccountant;

    private double mTotalMoney;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private List<Item> mItemList;

    public static Accountant get(Context context) {
        sAccountant = sAccountant == null ? new Accountant(context) : sAccountant;
        return sAccountant;
    }

    private Accountant(Context context) {
        mTotalMoney = 0;
        mContext = context.getApplicationContext();
        mDatabase = new ItemBaseHelper(mContext).getWritableDatabase();
        updateItemList();
    }

    private static ContentValues getContentValues(Item i) {
        ContentValues values = new ContentValues();
        values.put(ItemTable.cols.UUID, i.getUUID().toString());
        values.put(ItemTable.cols.NAME, i.getName());
        values.put(ItemTable.cols.PRICE, i.getPrice());
        values.put(ItemTable.cols.COUNT, i.getCount());

        return values;
    }

    public void addItem(Item latestItem) {
        if (mItemList.contains(latestItem)) {
            Item origItem = mItemList.get(mItemList.indexOf(latestItem));
            latestItem.setUUID(origItem.getUUID());
            latestItem.changeCountBy(origItem.getCount());

            String deleteDuplicate = "DELETE FROM " + ItemTable.NAME +
                                     " WHERE " + ItemTable.cols.NAME +
                                     "  = '"  + latestItem.getName() +
                                     "' AND " +  + latestItem.getPrice() +
                                     " = '" + ItemTable.cols.PRICE + "'";

           mDatabase.execSQL(deleteDuplicate);
            //mDatabase.delete(ItemTable.NAME, ItemTable.cols.NAME + " = ?", new String[] {latestItem.getName()});
        }
        mDatabase.insert(ItemTable.NAME, null, getContentValues(latestItem));
        updateItemList();
    }

    public void removeItem(int i) {
        mDatabase.delete(ItemTable.NAME, "_id = ?", new String[] {String.valueOf(i+1)});
        updateItemList();
    }

    public void addMoney(double money, boolean isSet) {
        mTotalMoney += isSet ? money - mTotalMoney : money;
    }

    public String getTotalMoney() {
        return MoneyUtils.prep(mTotalMoney);
    }

    public List<Item> getItemList() {
        return mItemList;
    }

    public String getChange() {
        double cost = 0;

        for (Item item : mItemList)
            cost += item.getPrice() * item.getCount();

        return MoneyUtils.prep(mTotalMoney - cost);
    }

     private void updateItemList() {
         mItemList = new ArrayList<>();

         ItemCursorWrapper cursor = queryItems(null, null);
         try {
             cursor.moveToFirst();
             while (!cursor.isAfterLast()) {
                 mItemList.add(cursor.getItem());
                 cursor.moveToNext();
             }
         } finally {
             cursor.close();
         }
     }

    public ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(ItemTable.NAME, null, whereClause, whereArgs, null, null, null);

        return new ItemCursorWrapper(cursor);
    }

    public String individualPriceOf(Item i) {
        return MoneyUtils.prep(i.getPrice());
    }

    public String totalPriceOf(Item i) {
       return MoneyUtils.prep(i.getCount() * i.getPrice());
    }

    public String countOf(Item i) {
       return String.valueOf(i.getCount());
    }

    public void clearList() {
        mDatabase.delete(ItemTable.NAME, null, null);
    }
}
