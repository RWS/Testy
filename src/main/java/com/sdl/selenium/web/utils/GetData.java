package com.sdl.selenium.web.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// The method returns the current date
public class GetData {
    public static String getToday(String format) {
        String ret = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        ret = sdf.format(new Date());
        return ret;
    }

    public static String getYear() {
        String ret = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        ret = sdf.format(new Date());
        return ret;
    }

    public static String getMonth() {
        String ret = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");
        ret = sdf.format(new Date());
        return ret;
    }

    public static String getDay() {
        String ret = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        ret = sdf.format(new Date());
        return ret;
    }

    /**
     * Get Current day (+/- number of days/months/years)
     * @param days
     * @param months
     * @param years
     * @param format
     * @return
     */
    public static String getDate(int days, int months, int years, String format) {
        return getDate(days, months, years, format, Locale.ENGLISH);
    }

     public static String getDate(int days, int months, int years, String format, Locale locale) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
        now.add(Calendar.DATE, +days);
        now.add(Calendar.MONTH, +months);
        now.add(Calendar.YEAR, +years);
        return formatter.format(now.getTime());
    }

    public static void main(String[] args) {
        //System.out.println(getToday());
    }
}
