package com.santinocampos.android.count.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.santinocampos.android.count.Models.Item;

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
                   " _id integer primary key autoincrement, " +
                   ItemTable.cols.NAME + " TEXT(255), " +
                   ItemTable.cols.PRICE + " TEXT(255), " +
                   ItemTable.cols.COUNT + " TEXT(255), " +
                   ItemTable.cols.TOTAL_PRICE + " INTEGER(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
