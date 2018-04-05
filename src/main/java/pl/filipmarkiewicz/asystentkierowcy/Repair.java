package pl.filipmarkiewicz.asystentkierowcy;

import java.util.Calendar;
import java.util.LinkedList;

import pl.filipmarkiewicz.filedatabase.FileDatabaseItem;
import pl.filipmarkiewicz.filedatabase.FileDatabaseManager;
import pl.filipmarkiewicz.filedatabase.FileDatabaseRow;

/**
 * Created by Filip on 2018-04-03.
 */

public class Repair implements FileDatabaseItem {
    int id;
    boolean done;
    String name;
    double expectedPrice;
    Calendar doneDate;
    double finalPrice;
    String description;

    public Repair(int id, boolean done, String name, double expectedPrice) {
        this.id = id;
        this.done = done;
        this.name = name;
        this.expectedPrice = expectedPrice;

        this.finalPrice = 0.0;
        this.doneDate = null;
        this.description = "";
    }

    public FileDatabaseRow toFileDatabaseRow() {
        int id = this.id;

        String[] data = {
                Base.parseBoolean(this.isDone()),
                getName(),
                getExpectedPrice() + "",
                Base.getDateInStandardFormat(this.doneDate),
                getFinalPrice() + "",
                getDescription()
        };

        FileDatabaseRow row = new FileDatabaseRow(id, data);

        return row;
    }

    public static Repair createFromFileDatabaseRow(FileDatabaseRow row) {
        int id = row.getId();
        boolean done = Base.getBooleanOfString(row.get(0));
        String name = row.get(1);
        double expectedPrice = Double.parseDouble(row.get(2));

        Repair repair = new Repair(id, done, name, expectedPrice);

        Calendar doneDate = Base.parseDate(row.get(3));
        double finalPrice = Double.parseDouble(row.get(4));
        String description = row.get(5);

        repair.setDoneDate(doneDate);
        repair.setFinalPrice(finalPrice);
        repair.setDescription(description);

        return repair;
    }

    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another == this) {
            return true;
        } else if (!(another instanceof Repair)) {
            return false;
        } else {
            Repair anotherRepair = (Repair) another;

            if (this.getId() == anotherRepair.getId()
                    && this.isDone() == anotherRepair.isDone()
                    && this.getName().equals(anotherRepair.getName())
                    && this.getExpectedPrice() == anotherRepair.getExpectedPrice()
                    && this.getFinalPrice() == anotherRepair.getFinalPrice()
                    && this.getDescription().equals(anotherRepair.getDescription())) {

                    if (this.getDoneDate() == null && anotherRepair.getDoneDate() == null
                            || (this.getDoneDate() != null && this.getDoneDate().equals(anotherRepair.getDoneDate()))) {
                        return true;
                    } else {
                        return false;
                    }
            } else {
                return false;
            }
        }
    }

    public int getId() {
        return this.id;
    }

    public boolean isDone() {
        return this.done;
    }

    public Repair setDone(boolean done) {
        this.done = done;

        return this;
    }

    public String getName() {
        return this.name;
    }

    public Repair setName(String name) {
        this.name = name;

        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Repair setDescription(String description) {
        this.description = description;

        return this;
    }

    public double getExpectedPrice() {
        return this.expectedPrice;
    }

    public Repair setExpectedPrice(double expectedPrice) {
        this.expectedPrice = expectedPrice;

        return this;
    }

    public Calendar getDoneDate() {
        return this.doneDate;
    }

    public Repair setDoneDate(Calendar doneDate) {
        this.doneDate = doneDate;

        return this;
    }

    public double getFinalPrice() {
        return this.finalPrice;
    }

    public Repair setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;

        return this;
    }

    @Override
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

    public String toString() {
        String result;

        if (isDone()) {
            result = this.name + " - " + this.getFinalPrice() + "zł";
        } else {
            result = this.name + " - " + this.getExpectedPrice() + "zł";
        }

        return result;
    }
}
