package com.santinocampos.android.count.Database;

/**
 * Created by thedr on 12/21/2016.
 */
public class ItemDbSchema {
     public static final class ItemTable  {
         public static final String NAME = "items";

         public static final class cols {
             public static final String NAME = "name";
             public static final String PRICE = "price";
             public static final String COUNT = "count";
         }
     }
}
