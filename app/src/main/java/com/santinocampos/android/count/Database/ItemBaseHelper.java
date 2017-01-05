package com.santinocampos.android.count.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.santinocampos.android.count.Models.ItemType;

import static com.santinocampos.android.count.Database.ItemDbSchema.ItemTable;

/**
 * Created by thedr on 12/21/2016.
 */
public class ItemBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
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
                   ItemTable.cols.COUNT + " INTEGER(255), " +
                   ItemTable.cols.TOTAL_PRICE + " REAL(255), " +
                   ItemTable.cols.ITEM_TYPE + " INTEGER(255));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
             String tableColumns =
                    ItemTable.cols.NAME + ", " +
                    ItemTable.cols.PRICE + ", " +
                    ItemTable.cols.COUNT + ", " +
                    ItemTable.cols.TOTAL_PRICE;
            if (newVersion == 2) {
                db.execSQL("ALTER TABLE " + ItemTable.NAME + " RENAME TO tempOldTable");

                onCreate(db);
                db.execSQL("INSERT INTO " + ItemTable.NAME + "(" + tableColumns + ") SELECT " +
                           tableColumns + " FROM tempOldTable " + ";" );
                db.execSQL("UPDATE " + ItemTable.NAME + " SET " + ItemTable.cols.ITEM_TYPE +
                           " = '" + ItemType.NO_TYPE.getItemTypeNameID() + "' WHERE " + ItemTable.cols.ITEM_TYPE +
                           " = NULL;");
                db.execSQL("DROP TABLE tempOldTable");
            }
        }
    }
}
