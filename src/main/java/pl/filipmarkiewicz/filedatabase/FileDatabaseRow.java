package pl.filipmarkiewicz.filedatabase;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.filipmarkiewicz.asystentkierowcy.Base;

/**
 * Created by Filip on 2018-03-26.
 */

public class FileDatabaseRow {
    public final static String SEPARATOR = "|";

    private int id;
    private String[] data;

    public FileDatabaseRow(int id, String[] data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return this.id;
    }

    public FileDatabaseRow setId(int id) {
        this.id = id;

        return this;
    }

    public String[] getData() {
        return this.data;
    }

    public FileDatabaseRow setData(String[] newData) {
        this.data = newData;

        return this;
    }

    public String get(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= data.length) {
            throw new IndexOutOfBoundsException();
        }

        return this.data[i];
    }

    public static FileDatabaseRow createFromLine(String line) {
        if (validateLine(line)) {
            String[] parts = Base.split(line, SEPARATOR);

            int id = Integer.parseInt(parts[0]);
            String[] data = Arrays.copyOfRange(parts, 1, parts.length);

            return new FileDatabaseRow(id, data);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        String result = id + "";

        result += this.SEPARATOR + Base.implode(this.data, this.SEPARATOR);

        return result;
    }

    public static boolean validateLine(String line) {
        Pattern pattern = Pattern.compile("[0-9]+" + SEPARATOR + ".*");

        Matcher matcher = pattern.matcher(line);

        return matcher.find();
    }

    public static boolean validateDatum(String data){
        Pattern pattern = Pattern.compile("[" + SEPARATOR + "]+");

        Matcher matcher = pattern.matcher(data);

        return !matcher.find();
    }
}
