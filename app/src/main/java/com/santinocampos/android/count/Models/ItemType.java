package com.santinocampos.android.count.Models;

import com.santinocampos.android.count.R;

/**
 * Created by thedr on 1/2/2017.
 */

public enum ItemType {
    GADGETS(R.drawable.ic_gadget, "GADGET"),
    FOOD_DRINK(R.drawable.ic_food_drink, "FOOD&DRINK"),
    ENTERTAINMENT(R.drawable.ic_entertainment, "ENTERTAINMENT"),
    MISC_PAYMENTS(R.drawable.ic_misc_payments, "MISC_PAYMENTS"),
    NO_TYPE(R.drawable.ic_no_type, "NO_TYPE"),
    SCHOOL_SUPPLIES(R.drawable.ic_school_supplies, "SCHOOL_SUPPLIES");

    private String mTypeName;
    private int mImageID;

    ItemType(int imageID, String itemTypeName) {
        this.mImageID = imageID;
        this.mTypeName = itemTypeName;
    }

    public int getImageID() {
        return mImageID;
    }

    public String getTypeName() {
        return mTypeName;
    }
}
