package com.santinocampos.android.count.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.santinocampos.android.count.Database.ItemDbSchema.ItemTable;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.ItemType.ItemType;

/**
 * Created by thedr on 12/25/2016.
 */

public class ItemCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Item getItem() {
        String itemName = getString(getColumnIndex(ItemTable.cols.NAME));
        double itemPrice = getDouble(getColumnIndex(ItemTable.cols.PRICE));
        int itemCount = getInt(getColumnIndex(ItemTable.cols.COUNT));
        int itemTypeNameID = getInt(getColumnIndex(ItemTable.cols.ITEM_TYPE));
        return new Item(itemName, itemPrice, itemCount, ItemType.typeNamed(itemTypeNameID));
    }
}
