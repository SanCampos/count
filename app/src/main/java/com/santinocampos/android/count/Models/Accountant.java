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

    public static Accountant get(Context context) {
        sAccountant = sAccountant == null ? new Accountant(context) : sAccountant;
        return sAccountant;
    }

    private Accountant(Context context) {
        mTotalMoney = 0;
        mContext = context.getApplicationContext();
        mDatabase = new ItemBaseHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Item i) {
        ContentValues values = new ContentValues();
        values.put(ItemTable.cols.NAME, i.getName());
        values.put(ItemTable.cols.PRICE, i.getPrice());
        values.put(ItemTable.cols.COUNT, i.getCount());

        return values;
    }
    public void addItem(Item item) {
        ContentValues cv = getContentValues(item);
        mDatabase.insert(ItemTable.NAME, null, cv);
    }

    public void removeItem(int i) {

    }

    public void addMoney(double money, boolean isSet) {
        mTotalMoney += isSet ? money - mTotalMoney : money;
    }

    public String getTotalMoney() {
        return MoneyUtils.prep(mTotalMoney);
    }

    public String getChange() {
        double cost = 0;

        for (Item item : new ArrayList<Item>())
            cost += totalPriceOf(item);

        return MoneyUtils.prep(mTotalMoney - cost);
    }

     public List<Item> getItemList() {
         List<Item> itemList = new ArrayList<>();

         ItemCursorWrapper cursor = queryItems(null, null);
         try {
             cursor.moveToFirst();
             while (!cursor.isAfterLast()) {
                 cursor.moveToNext();
                 itemList.add(cursor.getItem());
             }
         } finally {
             cursor.close();
         }
         return itemList;
     }

    public ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(ItemTable.NAME, null, whereClause, whereArgs, null, null, null);

        return new ItemCursorWrapper(cursor);
    }

    public double individualPriceOf(Item i) {
        return i.getPrice();
    }

    public double totalPriceOf(Item i) {
       return 0;
    }

    public int countOf(Item i) {
       return 0;
    }

    public void clearList() {
        mDatabase.delete(ItemTable.NAME, null, null);
    }
}
