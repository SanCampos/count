package com.santinocampos.android.count.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.santinocampos.android.count.Database.ItemBaseHelper;
import com.santinocampos.android.count.Database.ItemCursorWrapper;
import com.santinocampos.android.count.Database.ItemDbSchema.ItemTable;
import com.santinocampos.android.count.Utils.DecUtils;
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
        mItemList = new ArrayList<>();
        updateItemList();
    }

    private static ContentValues getContentValues(Item i) {
        ContentValues values = new ContentValues();
        values.put(ItemTable.cols.NAME, i.getName());
        values.put(ItemTable.cols.PRICE, i.getPrice());
        values.put(ItemTable.cols.COUNT, i.getCount());
        values.put(ItemTable.cols.TOTAL_PRICE, i.getPrice() * i.getCount());
        values.put(ItemTable.cols.ITEM_TYPE, i.getItemType().getItemTypeNameID());
        return values;
    }

    public void addItem(Item latestItem) {
        //String sql;
        if (mItemList.contains(latestItem)) {
            Item origItem = mItemList.get(mItemList.indexOf(latestItem));
            latestItem.increaseCountBy(origItem.getCount());

            /**sql = "UPDATE " + ItemTable.NAME +
                              " SET " + ItemTable.cols.COUNT + " = " + String.valueOf(latestItem.getCount()) +
                              " WHERE " + ItemTable.cols.NAME + " = '" + String.valueOf(latestItem.getItemName()) +
                              "' AND " + ItemTable.cols.PRICE + " = '" + String.valueOf(latestItem.getItemPrice()) +
                              "';"; **/

            mDatabase.update(ItemTable.NAME, getContentValues(latestItem),
                             ItemTable.cols.NAME + " = ? AND " +
                             ItemTable.cols.PRICE + " = ?", new String[] {latestItem.getName(),
                             String.valueOf(latestItem.getPrice())});
         } else {
           /** sql = "INSERT INTO " + ItemTable.NAME + " (" + ItemTable.cols.NAME + ", " +
                         ItemTable.cols.PRICE + ", " + ItemTable.cols.COUNT + ") " +
                         "VALUES ('" + latestItem.getItemName() + "', '" + latestItem.getItemPrice() + "', '" +
                         latestItem.getCount() + "');";**/

            mDatabase.insert(ItemTable.NAME, null, getContentValues(latestItem));
        }
        //mDatabase.execSQL(sql);
        updateItemList();
    }

    public void removeItem(String itemName, String itemPrice) {
        mDatabase.delete(ItemTable.NAME, ItemTable.cols.NAME + " = ? AND " +
                                         ItemTable.cols.PRICE + " = ?" , new String[] {itemName, itemPrice});
        updateItemList();
    }

    public void addMoney(double money, boolean isSet) {
        mTotalMoney += isSet ? money - mTotalMoney : money;
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
         mItemList.clear();

         ItemCursorWrapper cursor = querySortedItems(null, null);
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

    public ItemCursorWrapper querySortedItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(ItemTable.NAME, null, whereClause, whereArgs, null, null, ItemTable.cols.TOTAL_PRICE + " DESC");
        return new ItemCursorWrapper(cursor);
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
        mDatabase.delete(ItemTable.NAME, null, null);
        updateItemList();
    }
}
