package com.fxecocal.free.Utility;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on menu1/menu4/2016.
 */
public class TimeUtility {
    ///////////////convert date
    public static long[] getHours(String start, String end) {
        long[] difs = new long[2];

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm a");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(start);
            d2 = format.parse(end);

            // in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            difs[0] = diffHours;
            difs[1] = diffMinutes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return difs;

    }
    public static int getOffset() {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

//        TimeZone.getDefault().getDisplayName();
        return tz.getRawOffset() / 3600 / 1000;
    }
    public static TimeZone getTimeZone() {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        TimeZone.getDefault().getDisplayName();
        return tz;
    }
    public static String convertTimeZone(String currentTime) {
//        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sourceFormat = new SimpleDateFormat("MMM/dd/yyyy");
        sourceFormat.setTimeZone(getTimeZone());
        Date parsed = null; // => Date is in UTC now "2011-03-01 15:10:37"
        try {
            parsed = sourceFormat.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//		TimeZone tz = TimeZone.getTimeZone("America/Chicago");
        TimeZone timeZone = TimeZone.getTimeZone("GMT-menu4:00");
        SimpleDateFormat destFormat = new SimpleDateFormat("MMM dd, yyyy");
        destFormat.setTimeZone(timeZone);

        String result = destFormat.format(parsed);
        return result;
    }

    public static String dateWithCustomFormat(String date, String curFormat,
                                              String format) {
        DateFormat dateFormat = new SimpleDateFormat(curFormat);
        Date convertedDate = null;

        try {
            convertedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat1 = new SimpleDateFormat(format);

        return dateFormat1.format(convertedDate);
    }
    public static String parseDateFormat(String dateString) {
        if (dateString == null) {
            return "";
        }
        Date date = null;

        String mDate = dateString;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a",
                Locale.ENGLISH);

        try {
            if (date == null) {
                date = dateFormat.parse(dateString);
            }
            String mdateString = dateFormat.format(date);
            date = dateFormat.parse(mdateString);
            Date currentdate = new Date();
            String when = dateFormat.format(currentdate);
            currentdate = dateFormat.parse(when);

            long diffInMillisec = currentdate.getTime() - date.getTime();
            long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
            long seconds = diffInSec % 60;
            diffInSec /= 60;
            long minutes = diffInSec % 60;
            diffInSec /= 60;
            long hours = diffInSec % 24;
            diffInSec /= 24;
            long days = diffInSec % 30;
            diffInSec /= 30;
            long months = diffInSec % 12;
            diffInSec /= 12;
            long year = diffInSec;
            mDate = String.valueOf(seconds);
            if (year > 0) {

                int length = mdateString.lastIndexOf(":");
                mdateString = mdateString.substring(0, length);
                mdateString = mdateString.replace(";", " at ");
                mDate = mdateString;
            } else if (months > 0) {

                int length = mdateString.lastIndexOf(":");
                mdateString = mdateString.substring(0, length);
                mdateString = mdateString.replace(";", " at,");
                mDate = mdateString;
            } else if (days > 1) {

                int length = mdateString.lastIndexOf(":");
                mdateString = mdateString.substring(0, length);
                mdateString = mdateString.replace(";", " at,");
                mDate = mdateString;
            } else if (days == 1) {
                int end = mdateString.lastIndexOf(";");
                int length = mdateString.lastIndexOf(":");
                mDate = "Yesterday at, "
                        + mdateString.substring(end + 1, length);
            } else if (days > 0) {
                mDate = String.valueOf(days) + " days ago";
            } else if (hours > 0) {
                mDate = String.valueOf(hours) + " hours ago";
            } else {
                mDate = String.valueOf(minutes) + " minutes ago";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDate;
    }
    public static String countTime(long timeStamp){
        String str = "";
//        if(timeStamp < 60){
//            str = "00:" + String.valueOf(timeStamp) ;
//            str = getDatewithFormat(str, "mm:ss", "mm:ss");
//            return str + " s";
//        }

        int second = (int)(timeStamp);
        int result = second;

        if((int)(result / 60) < 60){
            str = String.valueOf((int) (result / 60)) + ":" + result % 60;
            str = getDatewithFormat(str, "mm:ss", "mm:ss");
            return str + " s";

        } else {
            result = (int)(second / 3600);
            if(result < 24){
                str = String.valueOf(result) + ":" + (int)((second % 3600) / 60) + " m";
                str = getDatewithFormat(str, "hh:mm", "hh:mm");
                return str + " m";
            }else{
                result = (int)(second /  (3600 * 24));
                if(result < 7){
                    str = String.valueOf(result);
                    if (result == 1) {
                        str = str  + " Day";
                    } else {
                        str = str  + " Days";
                    }
                }else{
                    result = (int)(second / (3600 * 24 * 7));
                    if(result < 4){
                        str = String.valueOf(result);
                        if (result == 1) {
                            str = str  + " Week";
                        } else {
                            str = str  + " Weeks";
                        }
                    }else {
                        result = (int)(second / (3600 * 24 * 30));
                        if(result < 12){
                            str = String.valueOf(result);
                            if (result == 1) {
                                str = str  + " Month";
                            } else {
                                str = str  + " Months";
                            }
                        }else {
                            result = (int)(second / (3600 * 24 * 365));
                            str = String.valueOf(result);
                            if (result == 1) {
                                str = str  + " Year";
                            } else {
                                str = str  + " Years";
                            }
                        }
                    }
                }
            }
        }

        return str;
    }
    @SuppressLint("SimpleDateFormat")
    public static Date getMyDateFromGMTString(String gmtDateString) {
        Date date = null;
        try {
            DateFormat df = new SimpleDateFormat("dd/MMM/yyyy'T'HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            date = df.parse(gmtDateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDateStringFromTimeStampSecond(long time, String formatString) {

        DateFormat dateFormat = new SimpleDateFormat(formatString);
        Date date = new Date(time * 1000);
        String strDate = dateFormat.format(date).toString();

        return strDate;
    }
    public static String getDateStringFromTimeStampMillis(long time, String formatString) {

        DateFormat dateFormat = new SimpleDateFormat(formatString);
        Date date = new Date(time);
        String strDate = dateFormat.format(date).toString();

        return strDate;
    }

    public static int getWeedDayNumberFromDateString(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date)dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek;
    }
    public static String getWeekdayString(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE");
        String weekday = formatter.format(date);///Mon, Thu,....
        return weekday;
    }
    public static long getCurrentTimeStamp() {

        double timestamp = System.currentTimeMillis() / 1000f;

        return (long) timestamp;
    }
    public static Long getTimeStampFromString(String str_date) {/////good works
        str_date = str_date.replace("-", "/");
        DateFormat formatter = new SimpleDateFormat("MMM/dd/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = null;
        try {
            date = (Date)formatter.parse(str_date);
//            date = getMyDateFromGMTString(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println("Today is " +date.getTime());
        return date.getTime() / 1000;
    }

    public static String getDatewithFormat(String time, String inputPattern, String outputPattern) {///////////good works

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
//
//            date = outputFormat.parse(time);
//            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static String dateFormatter(String time) {///////////good works

        String inputPattern = "dd/MMM/yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
//
//            date = outputFormat.parse(time);
//            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static String formatMonthFromNumberToString(int month) {///////////good works

        String inputPattern = "MM";
        String outputPattern = "MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(String.valueOf(month));
            str = outputFormat.format(date);
//
//            date = outputFormat.parse(time);
//            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static String getDatewithFormat2(Date date) {
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MMM/yyyy");
        String showDate1 = df2.format(date);

        return showDate1;
    }

    public static String formatterToDateMonth(String time) {///////////good works


        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "MMM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
//
//            date = outputFormat.parse(time);
//            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;


    }
    public static String formatterToHour(String time) {///////////good works


        String inputPattern = "hh:mm:ss";
        String outputPattern = "hh";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
//
//            date = outputFormat.parse(time);
//            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;


    }
    public static String getHourFromTimeStamp(String timstamp) {

        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date date = new Date(Long.parseLong(timstamp) * 1000);
        String strDate = dateFormat.format(date).toString();

        return strDate;
    }

    public static int getCurrentDAY() {
        // TODO Auto-generated method stub
        int showhours = 1;
        try {
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            Date mDate = cal.getTime();
            SimpleDateFormat df2 = new SimpleDateFormat("dd");
            String date = df2.format(mDate);
            showhours = Integer.parseInt(date);
        } catch (Exception e) {

        }
        return showhours;
    }

    public static int getCurrentYear() {
        // TODO Auto-generated method stub
        int showhours = 1;
        try {
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            Date mDate = cal.getTime();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy");
            String date = df2.format(mDate);
            showhours = Integer.parseInt(date);
        } catch (Exception e) {

        }
        return showhours;
    }
    public static Integer getdaysFromdate(String date) {
        int days = 1;
        try {
            Date mDate = new Date(date.replace("-", "/"));



            SimpleDateFormat df = new SimpleDateFormat("dd");
            String sdays = df.format(mDate);
            days = Integer.parseInt(sdays);
        } catch (Exception e) {

        }
        return days;

    }
    public static String get_count_of_days(String Created_date_String,String Expire_date_String)
    {


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());

        Date Created_convertedDate=null, Expire_CovertedDate=null, todayWithZeroTime=null;
        try
        {
            Created_convertedDate = dateFormat.parse(Created_date_String);
            Expire_CovertedDate = dateFormat.parse(Expire_date_String);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e)
        {
            e.printStackTrace();
        }


        int c_year = 0,c_month = 0,c_day = 0;

        if(Created_convertedDate.after(todayWithZeroTime))
        {
            Calendar c_cal = Calendar.getInstance();
            c_cal.setTime(Created_convertedDate);

            c_year = c_cal.get(Calendar.YEAR);
            c_month = c_cal.get(Calendar.MONTH);
            c_day = c_cal.get(Calendar.DAY_OF_MONTH);

        }
        else
        {
            Calendar c_cal = Calendar.getInstance();
            c_cal.setTime(todayWithZeroTime);

            c_year = c_cal.get(Calendar.YEAR);
            c_month = c_cal.get(Calendar.MONTH);
            c_day = c_cal.get(Calendar.DAY_OF_MONTH);
        }


            /*Calendar today_cal = Calendar.getInstance();
            int today_year = today_cal.get(Calendar.YEAR);
            int today = today_cal.get(Calendar.MONTH);
            int today_day = today_cal.get(Calendar.DAY_OF_MONTH);
            */





        Calendar e_cal = Calendar.getInstance();
        e_cal.setTime(Expire_CovertedDate);

        int e_year = e_cal.get(Calendar.YEAR);
        int e_month = e_cal.get(Calendar.MONTH);
        int e_day = e_cal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(c_year, c_month, c_day);
        date2.clear();
        date2.set(e_year, e_month, e_day);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        if (diff < 0) {
            return "";
        }
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);


        return String.valueOf((int) dayCount);
    }

}
