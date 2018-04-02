package pl.filipmarkiewicz.asystentkierowcy;

import android.content.Context;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import pl.filipmarkiewicz.filedatabase.FileDatabaseItem;
import pl.filipmarkiewicz.filedatabase.FileDatabaseManager;
import pl.filipmarkiewicz.filedatabase.FileDatabaseRow;

/**
 * Created by Filip on 2018-03-21.
 */

public class Refueling implements FileDatabaseItem {
    private int id;
    private FuelType fuelType;
    private double amount;
    private double price;
    private Calendar date;

    public Refueling(int id, FuelType fuelType, double amount, double price, Calendar date) {
        this.id = id;
        this.fuelType = fuelType;
        this.amount = amount;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public FuelType getFuelType() {
        return this.fuelType;
    }

    public Refueling setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;

        return this;
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

    public Refueling setPrice(double price) {
        this.price = price;

        return this;
    }

    public Calendar getDate() {
        return this.date;
    }

    public Refueling setDate(Calendar date) {
        this.date = date;

        return this;
    }

    public static String getFuelTypeName(FuelType type) {
        if (type == FuelType.PETROL_PLAIN) {
            return "Benzyna";
        } else if (type == FuelType.DIESEL_PLAIN) {
            return "Diesel";
        } else if (type == FuelType.DIESEL_VERVA) {
            return "Diesel Verva";
        } else {
            return "Niezdefiniowany";
        }
    }

    public static String getFuelTypeSymbol(FuelType type) {
        if (type == FuelType.PETROL_PLAIN) {
            return "B";
        } else if (type == FuelType.DIESEL_PLAIN) {
            return "D";
        } else if (type == FuelType.DIESEL_VERVA) {
            return "DV";
        } else {
            return "N";
        }
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
                this.getFuelType().getId() + "",
                this.getAmount() + "",
                this.getPrice() + "",
                this.getDateInStandardFormat(this.getDate())
        };

        FileDatabaseRow row = new FileDatabaseRow(this.id, data);

        return row;
    }

    public static Refueling createFromFileDatabaseRow(FileDatabaseRow row) {
        int id = row.getId();

        FuelType fuelType = FuelType.parseType(row.get(0));
        double amount = Double.parseDouble(row.get(1));
        double price = Double.parseDouble(row.get(2));
        Calendar calendar = parseDate(row.get(3));

        return new Refueling(id, fuelType, amount, price, calendar);
    }

    /**public static Refueling createFromFileLine(String line) {
        String[] data = Base.split(line, FileDatabaseManager.SEPARATOR);

        double amount = Double.parseDouble(data[0]);
        double price = Double.parseDouble(data[1]);

        Calendar date = parseDate(data[2]);

        return new Refueling(amount, price, date);
    }**/

    @Override
    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another == this) {
            return true;
        } else if (!(another instanceof Refueling)) {
            return false;
        } else {
            Refueling anotherRefueling = (Refueling) another;

            if (this.getId() == anotherRefueling.getId()
                    && this.getFuelType() == anotherRefueling.getFuelType()
                    && this.getAmount() == anotherRefueling.getAmount()
                    && this.getPrice() == anotherRefueling.getPrice()
                    && this.getDate().equals(anotherRefueling.getDate())) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean save(FileDatabaseManager fdm) {
        try {
            LinkedList<FileDatabaseRow> rows = fdm.read();

            FileDatabaseRow wanted = fdm.get(this.getId());
            FileDatabaseRow current = this.toFileDatabaseRow();

            if (wanted == null) {
                current = fdm.add(current);

                this.id = current.getId();
            } else {
                fdm.put(current);
            }

            return fdm.write();
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return getFuelTypeSymbol(fuelType) + " | " + getDateInStandardFormat(date) + " | " + amount + " l | " + price + " zł";
    }
}
