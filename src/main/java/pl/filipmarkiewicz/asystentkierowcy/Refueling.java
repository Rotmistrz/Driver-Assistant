package pl.filipmarkiewicz.asystentkierowcy;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Filip on 2018-03-21.
 */

public class Refueling {
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
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        return sdf.format(date.getTime());
    }

    public String toFileLine() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }
}
