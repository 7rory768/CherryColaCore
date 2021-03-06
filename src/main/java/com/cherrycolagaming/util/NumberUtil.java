package com.cherrycolagaming.util;

import java.text.DecimalFormat;

public class NumberUtil {

    private static String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };

    public static boolean isInt(String arg) {
        try {
            Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isPositiveInt(String arg) {
        try {
            int i = Integer.parseInt(arg);
            return i >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNegativeInt(String arg) {
        try {
            int i = Integer.parseInt(arg);
            return i < 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String arg) {
        try {
            Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isPositiveDouble(String arg) {
        try {
            double i = Double.parseDouble(arg);
            return i >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNegativeDouble(String arg) {
        try {
            double i = Double.parseDouble(arg);
            return i < 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String formatMoney(String money) {
        int eIndex = money.indexOf("E");
        if (eIndex != -1) {
            int eValue = Integer.valueOf(money.substring(eIndex + 1, money.length()));
            money = money.substring(0, eIndex).replace(".", "");
            int bound = (eValue - money.length()) + 1;
            if (money.length() < eValue) {
                for (int i = 0; i < bound; i++) {
                    money += "0";
                }
            }
        }
        String beforeDot = money;

        if (money.contains(".")) {
            beforeDot = money.substring(0, money.indexOf("."));
        }

        String newBeforeDot = beforeDot;
        for (int i = beforeDot.length() - 3; i > 0; i -= 3) {
            newBeforeDot = beforeDot.substring(0, i) + "," + newBeforeDot.substring(i, newBeforeDot.length());
        }
        return newBeforeDot;
    }

    public static String setMaxDecimals(double arg, int places) {
        String suffix = "";
        for (int i = 0; i < places; i++) {
            suffix += "#";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0." + suffix);
        return decimalFormat.format(arg);
    }

    public static String ordinal(int i) {
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];

        }
    }
}
