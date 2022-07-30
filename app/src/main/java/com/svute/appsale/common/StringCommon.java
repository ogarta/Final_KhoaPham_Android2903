package com.svute.appsale.common;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.DecimalFormat;

/**
 * Created by Ogata on 7/25/2022.
 */
public class StringCommon {

    public static boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    /**
     * Format Currency By 0,000
     *
     * @param number int
     * @return String
     */
    public static String formatCurrency(int number) {
        return new DecimalFormat("#,###").format(number);
    }

    /**
     * Format Currency By YYYY-MM-DD HH:MM
     *
     * @param date String
     * @return String
     */
    public static String formatDate(String date) {
        String[] _date = date.split("T");
        String[] time = _date[1].split(":");
        int hour = Integer.parseInt(time[0])+7;
        return _date[0]+" "+ hour+":"+time[1];
    }

}
