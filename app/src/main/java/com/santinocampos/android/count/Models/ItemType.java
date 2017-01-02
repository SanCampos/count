package com.santinocampos.android.count.Models;

import com.santinocampos.android.count.R;

/**
 * Created by thedr on 1/2/2017.
 */

public enum ItemType {
    GADGETS(R.drawable.ic_gadget, R.string.Gadgets),
    FOOD_DRINK(R.drawable.ic_food_drink, R.string.Food_Drink),
    ENTERTAINMENT(R.drawable.ic_entertainment, R.string.Entertainment),
    MISC_PAYMENTS(R.drawable.ic_misc_payments, R.string.Misc_Payment),
    NO_TYPE(R.drawable.ic_no_type, R.string.No_Type),
    SCHOOL_SUPPLIES(R.drawable.ic_school_supplies, R.string.School_Supplies);

    private int mTypeName;
    private int mImageID;

    ItemType(int imageID, int itemTypeName) {
        this.mImageID = imageID;
        this.mTypeName = itemTypeName;
    }

    public int getImageID() {
        return mImageID;
    }

    public int getTypeName() {
        return mTypeName;
    }
}
