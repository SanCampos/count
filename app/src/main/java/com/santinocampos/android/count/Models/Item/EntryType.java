package com.santinocampos.android.count.Models.Item;

import com.santinocampos.android.count.R;

/**
 * Created by thedr on 1/2/2017.
 */

public enum EntryType {

    FOOD_DRINK(R.drawable.ic_food_drink, R.string.itemType_food_drink),
    ENTERTAINMENT(R.drawable.ic_entertainment, R.string.itemType_entertainment),
    GADGETS(R.drawable.ic_gadget, R.string.itemType_gadgets),
    MISC_PAYMENTS(R.drawable.ic_misc_payments, R.string.itemType_misc_payment),
    SCHOOL_SUPPLIES(R.drawable.ic_school_supplies, R.string.itemType_school_supplies),
    NO_TYPE(R.drawable.ic_no_type, R.string.no_type);

    private int mTypeNameID;
    private int mImageID;

    EntryType(int imageID, int itemTypeName) {
        this.mImageID = imageID;
        this.mTypeNameID = itemTypeName;
    }

    public int getImageID() {
        return  mImageID;
    }

    public static int getImageIdOf(int itemType) {
        return values()[itemType].getImageID();
    }

    public int getItemTypeNameID() {
        return mTypeNameID;
    }
}
