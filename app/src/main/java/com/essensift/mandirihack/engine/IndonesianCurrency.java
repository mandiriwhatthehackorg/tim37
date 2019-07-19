package com.essensift.mandirihack.engine;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class IndonesianCurrency {

    public static String toIndonesianCurrencyWithSymbol(Long d) {
        return toIndonesianCurrencyWithSymbol((double) d);
    }

    public static String toIndonesianCurrencyWithSymbol(double d) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return String.format("%s %n", kursIndonesia.format(d)).trim();
    }

    public static String toIndonesianCurrencyWithoutSymbol(Long d) {
        return toIndonesianCurrencyWithoutSymbol((double) d);
    }

    public static String toIndonesianCurrencyWithoutSymbol(double d) {
        Locale localeID = new Locale("in", "ID");
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(localeID);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(d).trim();
    }
}
