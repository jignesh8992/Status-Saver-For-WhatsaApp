package com.jignesh.jndroid.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {


    public static String pattern_1 = "yyyy.MM.dd G 'at' HH:mm:ss z";  // 2001.07.04 AD at
    // 12:08:56 PDT
    public static String pattern_2 = "EEE, MMM d, ''yy";  // Wed, Jul 4, '01
    public static String pattern_3 = "h:mm a";  // 12:08 PM
    public static String pattern_4 = "hh 'o''clock' a, zzzz";  // 12 o'clock PM, Pacific Daylight
    // Time
    public static String pattern_5 = "K:mm a, z";  // 0:08 PM, PDT
    public static String pattern_6 = "yyyyy.MMMMM.dd GGG hh:mm aaa";  // 02001.July.04 AD 12:08 PM
    public static String pattern_7 = "EEE, d MMM yyyy HH:mm:ss Z";  // Wed, 4 Jul 2001 12:08:56
    // -0700
    public static String pattern_8 = "yyMMddHHmmssZ";  // 010704120856-0700
    public static String pattern_9 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";  // 2001-07-04T12:08:56.235-0700
    public static String pattern_10 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";  //
    // 2001-07-04T12:08:56.235-07:00
    public static String pattern_11 = "YYYY-'W'ww-u";  // 2001-W27-3
    public static String pattern_12 = "EEE, MMM dd yyyy";  // Tue, May 05 2018
    public static String pattern_13 = "MM-dd";  // 05-31
    public static String pattern_14 = "YYYY";  // 2018
    public static String pattern_15 = "dd";  // 31
    public static String pattern_16 = "MM";  // 06
    public static String pattern_17 = "dd-MM-YYYY hh:mm aaa";  // 06
    public static String pattern_18 = "EEE, MMM dd HH:mm:ss yyyy";  // Tue, May 05 2018


    /**
     * ToDo.. The maximum date possible.
     */
    public static Date MAX_DATE = new Date(Long.MAX_VALUE);

    /*
     * ToDo.. get Time
     */
    public static String getTime(int seconds) {
        int sec = seconds;
        int min;
        int hr;
        String result = null;
        if (sec > 60) {
            min = (int) (sec / 60);
            if (min > 60) {
                hr = (int) (min / 60);
                result = hr + "h " + min % 60 + "m";
            } else {
                result = min + "m " + sec % 60 + "s";
            }
        } else {
            result = sec + " sec";
        }
        return result;
    }

    /**
     * Return date in specified format.
     *
     * @param milliSeconds Date in milliseconds
     * @param dateFormat   Date format
     * @return String representing date in specified format
     */
    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to
        // date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    public static Date getDate(long milliseconds) {
        return new Date(milliseconds);
    }


    /*
     * ToDo.. Return Current date in given Pattern
     */
    public static String getToday(String pattern) {
        return new SimpleDateFormat(pattern).format(getToday());
    }

    /*
     * ToDo.. Return date in given Pattern
     */
    public static String getDate(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /*
     * ToDo.. Return Current date
     */
    public static Date getToday() {
        return Calendar.getInstance().getTime();
    }


    public static String getStringDate(Date date) {
        String result;

        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");

        if (isToday(date)) {
            format = new SimpleDateFormat("hh:mm a");
            result ="Today " + format.format(date);
        } else if (isYesterDay(date)) {
            format = new SimpleDateFormat("hh:mm a");
            result = "Yesterday " + format.format(date);
        } else {
            format = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
            result = format.format(date);
        }

        return result;
    }

    public static String getStringDate2(Date date) {
        String result;
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
        if (isToday(date)) {
            result ="Today";
        } else if (isYesterDay(date)) {
            result = "Yesterday";
        } else {
            format = new SimpleDateFormat(pattern_12);
            result = format.format(date);
        }
        return result;
    }


    public static boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    public static boolean isToday(Calendar cal) {
        return isSameDay(cal, Calendar.getInstance());
    }

    public static boolean isYesterDay(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(yesterday());
        return isSameDay(cal1, cal2);
    }

    private static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static long milliseconds(String date) {
        //String date_ = date;
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeHelper.pattern_13);
        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public boolean isBeforeDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isBeforeDay(cal1, cal2);
    }

    public boolean isBeforeDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) return true;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) return false;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) return true;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) return false;
        return cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR);
    }

    public boolean isAfterDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isAfterDay(cal1, cal2);
    }

    public boolean isAfterDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) return false;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) return true;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) return false;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) return true;
        return cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * <p>
     * Checks if a date is after today and within a number of days in the
     * future.
     * </p>
     *
     * @param date the date to check, not altered, not null.
     * @param days the number of days.
     * @return true if the date day is after today and within days in the future
     * .
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public boolean isWithinDaysFuture(Date date, int days) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return isWithinDaysFuture(cal, days);
    }

    /**
     * <p>
     * Checks if a calendar date is after today and within a number of days in
     * the future.
     * </p>
     *
     * @param cal  the calendar, not altered, not null
     * @param days the number of days.
     * @return true if the calendar date day is after today and within days in
     * the future .
     * @throws IllegalArgumentException if the calendar is <code>null</code>
     */
    public boolean isWithinDaysFuture(Calendar cal, int days) {
        if (cal == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar today = Calendar.getInstance();
        Calendar future = Calendar.getInstance();
        future.add(Calendar.DAY_OF_YEAR, days);
        return (isAfterDay(cal, today) && !isAfterDay(cal, future));
    }

    /**
     * Returns the given date with the time set to the start of the day.
     */
    public Date getStart(Date date) {
        return clearTime(date);
    }

    /**
     * Determines whether or not a date has any time values (hour, minute,
     * seconds or millisecondsReturns the given date with the time values
     * cleared.
     */

    /**
     * Returns the given date with the time values cleared.
     */
    public Date clearTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Determines whether or not a date has any time values.
     *
     * @param date The date.
     * @return true iff the date is not null and any of the date's hour, minute,
     * seconds or millisecond values are greater than zero.
     */
    public boolean hasTime(Date date) {
        if (date == null) {
            return false;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.HOUR_OF_DAY) > 0) {
            return true;
        }
        if (c.get(Calendar.MINUTE) > 0) {
            return true;
        }
        if (c.get(Calendar.SECOND) > 0) {
            return true;
        }
        if (c.get(Calendar.MILLISECOND) > 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the given date with time set to the end of the day
     */
    public Date getEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * Returns the maximum of two dates. A null date is treated as being less
     * than any non-null date.
     */
    public Date max(Date d1, Date d2) {
        if (d1 == null && d2 == null) return null;
        if (d1 == null) return d2;
        if (d2 == null) return d1;
        return (d1.after(d2)) ? d1 : d2;
    }

    /**
     * Returns the minimum of two dates. A null date is treated as being greater
     * than any non-null date.
     */
    public Date min(Date d1, Date d2) {
        if (d1 == null && d2 == null) return null;
        if (d1 == null) return d2;
        if (d2 == null) return d1;
        return (d1.before(d2)) ? d1 : d2;
    }
}
