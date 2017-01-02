package com.santinocampos.android.count.Models;

import com.santinocampos.android.count.R;

/**
 * Created by thedr on 1/2/2017.
 */

public enum ItemType {
    GADGETS(R.drawable.ic_gadget),
    FOOD_DRINK(R.drawable.ic_food_drink),
    ENTERTAINMENT(R.drawable.ic_entertainment),
    MISC_PAYMENTS(R.drawable.ic_misc_payments),
    SCHOOL_SUPPLIES(R.drawable.ic_school_supplies);

    private int imageID;

    ItemType(int imageID) {
        this.imageID = imageID;
    }

    public int getImageID() {
        return imageID;
    }

}
