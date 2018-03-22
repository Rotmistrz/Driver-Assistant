package pl.filipmarkiewicz.asystentkierowcy;

/**
 * Created by Filip on 2018-03-22.
 */

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
            System.out.println(n);
            array.add(n);
            x = n + 1;
        }

        String[] items = new String[array.size() + 1];

        items[0] = str.substring(0, array.get(0));
        System.out.println(items[0]);
        int i = 1;

        while(i < array.size()) {
            items[i] = str.substring(array.get(i-1) + separator.length(), array.get(i));
            System.out.println(items[i]);
            i++;
        }

        if(str.endsWith(separator)) {
            items[i] = "";
            System.out.println(items[i]);
        } else {
            items[i] = str.substring(array.get(i-1) + separator.length());
            System.out.println(items[i]);
        }

        return items;
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