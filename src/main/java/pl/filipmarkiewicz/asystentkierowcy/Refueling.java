package pl.filipmarkiewicz.asystentkierowcy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Filip on 2018-03-21.
 */

public class Refueling implements FileDatabaseItem {
    private double amount;
    private double price;
    private Calendar date;

    public Refueling(double amount, double price, Calendar date) {
        this.amount = amount;
        this.price = price;
        this.date = date;
    }

    public double getAmount() {
        return this.amount;
    }

    public Refueling setAmount(double amount) {
        this.amount = amount;

        return this;
    }

    public double getPrice() {
        return this.price;
    }

    public Calendar getDate() {
        return this.date;
    }

    static public String getDateInStandardFormat(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(date.getTime());
    }

    public static Calendar parseDate(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = sdf.parse(str);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return calendar;
    }

    public String toFileLine() {
        return "";
    }

    public static Object createFromFileLine(String line) {
        String[] data = Base.split(line, FileDatabaseManager.SEPARATOR);

        double amount = Double.parseDouble(data[0]);
        double price = Double.parseDouble(data[1]);


        return new Refueling(0, 0, new GregorianCalendar());
    }

    @Override
    public String toString() {
        return "";
    }
}
