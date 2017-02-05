package com.santinocampos.android.count.Models;

/**
 * Created by thedr on 1/28/2017.
 */

public enum Currency {

    PHILIPPINE_PESO("Philippine Peso", 'P'),
    US_DOLLAR("U.S. Dollar", '$');

    private String name;
    private char symbol;

    Currency(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public static String[] currencyEntries() {
        String[] entries = new String[values().length];

        for (int i = 0; i < values().length; i++)
            entries[i] = values()[i].toString();

        return entries;
    }

    public static String[] currencyValues() {
        String[] entryValues = new String[values().length];

        for (int i = 0; i < values().length; i++)
            entryValues[i] = String.valueOf(i);

        return entryValues;
    }

    public String toString() {
        return name + " (" + symbol + ")";
    }
}
