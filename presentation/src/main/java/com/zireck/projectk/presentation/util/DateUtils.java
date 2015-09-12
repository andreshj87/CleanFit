package com.zireck.projectk.presentation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Zireck on 14/07/2015.
 */
public class DateUtils {

    public static final String DEFAULT_DATETIME_PATTERN = "yyyy/MM/dd H:mm";
    public static final String DEFAULT_DATE_PATTERN = "yyyy/MM/dd";
    public static final String DEFAULT_TIME_PATTERN = "H:mm";

    public static Date getDateTimeFromText(String text) throws ParseException {
        return getItFromText(DEFAULT_DATETIME_PATTERN, text);
    }

    public static Date getDateFromText(String text) throws ParseException {
        return getItFromText(DEFAULT_DATE_PATTERN, text);
    }

    public static Date getTimeFromText(String text) throws ParseException {
        return getItFromText(DEFAULT_TIME_PATTERN, text);
    }

    private static Date getItFromText(final String pattern, String text) throws ParseException {
        return new SimpleDateFormat(pattern).parse(text);
    }

    public static String getFormattedMealDate(Date date) {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(DEFAULT_DATETIME_PATTERN, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String getFormattedDayDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String getFormattedBirthdayDate(Date birthday) {
        SimpleDateFormat birthdayFormat =
                new SimpleDateFormat(DEFAULT_DATE_PATTERN, Locale.getDefault());
        return birthdayFormat.format(birthday);
    }

    public static int calculateAge(Date birthDate) {
        int years = 0;
        int months = 0;
        int days = 0;

        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);

        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);

        int currentMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        months = currentMonth - birthMonth;

        if (months < 0) {
            years--;
            months = 12 - birthMonth + currentMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                months--;
            }
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }

        // Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE)) {
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        } else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }

        return years;
    }
}
