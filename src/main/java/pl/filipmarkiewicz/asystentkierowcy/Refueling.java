package pl.filipmarkiewicz.asystentkierowcy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pl.filipmarkiewicz.filedatabase.FileDatabaseItem;
import pl.filipmarkiewicz.filedatabase.FileDatabaseManager;
import pl.filipmarkiewicz.filedatabase.FileDatabaseRow;

/**
 * Created by Filip on 2018-03-21.
 */

public class Refueling implements FileDatabaseItem {
    private int id;
    private double amount;
    private double price;
    private Calendar date;

    public Refueling(int id, double amount, double price, Calendar date) {
        this.id = id;
        this.amount = amount;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return this.id;
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

    public static Calendar parseDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar;

        try {
            Date date = sdf.parse(str);

            calendar = new GregorianCalendar();
            calendar.setTime(date);
        } catch(Exception e) {
            calendar = null;
        }

        return calendar;
    }

    @Override
    public FileDatabaseRow toFileDatabaseRow() {
        String[] data = {
                this.getAmount() + "",
                this.getPrice() + "",
                this.getDateInStandardFormat(this.getDate())
        };

        FileDatabaseRow row = new FileDatabaseRow(this.id, data);

        return row;
    }

    public static Refueling createFromFileDatabaseRow(FileDatabaseRow row) {
        int id = row.getId();

        double amount = Double.parseDouble(row.get(0));
        double price = Double.parseDouble(row.get(1));
        Calendar calendar = parseDate(row.get(2));

        return new Refueling(id, amount, price, calendar);
    }

    /**public static Refueling createFromFileLine(String line) {
        String[] data = Base.split(line, FileDatabaseManager.SEPARATOR);

        double amount = Double.parseDouble(data[0]);
        double price = Double.parseDouble(data[1]);

        Calendar date = parseDate(data[2]);

        return new Refueling(amount, price, date);
    }**/

    @Override
    public String toString() {
        return getDateInStandardFormat(date) + " | " + amount + " l | " + price + " zł";
    }
}
