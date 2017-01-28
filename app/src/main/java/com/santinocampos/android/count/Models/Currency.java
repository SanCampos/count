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

    public String toString() {
        return name + " (" + symbol + ")";
    }
}
