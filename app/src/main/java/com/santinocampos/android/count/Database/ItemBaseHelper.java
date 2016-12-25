package com.santinocampos.android.count.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.santinocampos.android.count.Database.ItemDbSchema.ItemTable;

/**
 * Created by thedr on 12/21/2016.
 */
public class ItemBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "itemBase.db";

    public ItemBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ItemTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ItemTable.cols.NAME + ", " +
                ItemTable.cols.PRICE + ", " +
                ItemTable.cols.COUNT +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}