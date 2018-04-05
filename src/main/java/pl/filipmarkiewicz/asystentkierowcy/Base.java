package pl.filipmarkiewicz.asystentkierowcy;

/**
 * Created by Filip on 2018-03-22.
 */

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base {
    private Base() {}

    public static String[] split(String str, String separator) {
        ArrayList<Integer> array = new ArrayList<>();
        int n;
        int x = 0;

        while(x < str.length() && (n = str.indexOf(separator, x)) != -1) {
            //System.out.println(n);
            array.add(n);
            x = n + 1;
        }

        String[] items = new String[array.size() + 1];

        items[0] = str.substring(0, array.get(0));
        //System.out.println(items[0]);
        int i = 1;

        while(i < array.size()) {
            items[i] = str.substring(array.get(i-1) + separator.length(), array.get(i));
            //System.out.println(items[i]);
            i++;
        }

        if(str.endsWith(separator)) {
            items[i] = "";
            //System.out.println(items[i]);
        } else {
            items[i] = str.substring(array.get(i-1) + separator.length());
            //System.out.println(items[i]);
        }

        return items;
    }

    public static String implode(String[] data, String glue) {
        String result = "";

        for (int i = 0; i < data.length; i++) {
            if (i != 0) {
                result += glue;
            }

            result += data[i];
        }

        return result;
    }

    static public String getDateInStandardFormat(Calendar date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            return sdf.format(date.getTime());
        }
    }

    public static Calendar parseDate(String str) {
        Calendar calendar;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = sdf.parse(str);

            if (date == null) {
                return null;
            } else {
                calendar = new GregorianCalendar();
                calendar.setTime(date);
            }
        } catch(Exception e) {
            calendar = null;
        }

        return calendar;
    }

    public static boolean getBooleanOfString(String str) {
        return (!str.equals("0")) ? true : false;
    }

    public static String parseBoolean(boolean something) {
        return (something) ? "1" : "0";
    }

    public static boolean isInt(String str) {
        String pattern = "^[\\-]?[0-9]+$";
        Pattern p = Pattern.compile(pattern);

        Matcher m = p.matcher(str);

        return (m.find()) ? true : false;
    }

    public static boolean isDouble(String str) {
        String pattern = "^[\\-]?[0-9]+([,|.]{1}[0-9]+)*$";
        Pattern p = Pattern.compile(pattern);

        Matcher m = p.matcher(str);

        return (m.find()) ? true : false;
    }
}