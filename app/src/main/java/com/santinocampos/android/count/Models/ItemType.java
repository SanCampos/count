package com.santinocampos.android.count.Models;

import android.content.Context;

import com.santinocampos.android.count.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thedr on 1/2/2017.
 */

public enum ItemType {

    GADGETS(R.drawable.ic_gadget, R.string.itemType_gadgets),
    FOOD_DRINK(R.drawable.ic_food_drink, R.string.itemType_food_drink),
    ENTERTAINMENT(R.drawable.ic_entertainment, R.string.itemType_entertainment),
    MISC_PAYMENTS(R.drawable.ic_misc_payments, R.string.itemType_misc_payment),
    NO_TYPE(R.drawable.ic_no_type, R.string.no_type),
    SCHOOL_SUPPLIES(R.drawable.ic_school_supplies, R.string.itemType_school_supplies);

    private int mTypeNameID;
    private int mImageID;

    ItemType(int imageID, int itemTypeName) {
        this.mImageID = imageID;
        this.mTypeNameID = itemTypeName;
    }

    public static ItemType typeNamed(int typeNameID) {
        for (ItemType i : values()) {
            if (typeNameID == i.getItemTypeNameID()) return i;
        }
        return NO_TYPE;
    }

    public int getImageID() {
        return mImageID;
    }

    public int getItemTypeNameID() {
        return mTypeNameID;
    }

    public static List<String> getItemNameList(Context c) {
        List<String> nameList = new ArrayList<>();

        for (ItemType i : values())
            nameList.add(c.getString(i.getItemTypeNameID()));

        return nameList;
    }
}
