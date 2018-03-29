package pl.filipmarkiewicz.filedatabase;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Filip on 2018-03-22.
 */

public class FileDatabaseManager {
    public final static String SEPARATOR = "|";
    private final static int INITIAL_AUTO_INCREMENT = 1;

    private Context context;
    private String filename;
    private HashMap<Integer, FileDatabaseRow> rows;

    private BufferedReader reader;
    private int autoIncrement;

    public FileDatabaseManager(String filename, Context context) {
        this.context = context;
        this.filename = filename;
        this.rows = new HashMap<Integer, FileDatabaseRow>();
        this.autoIncrement = INITIAL_AUTO_INCREMENT;
    }

    /**public boolean open() {
        try {
            reader = new BufferedReader(new FileReader(this.path));
        } catch (FileNotFoundException ex) {
            return false;
        }

        this.isOpen = true;

        return true;
    }

    public boolean close() {
        if (this.isOpen()) {
            try {
                reader.close();
            } catch (IOException ioe) {
                return false;
            }
        }

        return true;
    }**/

    public LinkedList<FileDatabaseRow> read() throws IOException, InvalidFileDatabaseLineException, InvalidFileDatabaseFormat {
        this.rows = null;
        this.rows = new HashMap<Integer, FileDatabaseRow>();

        LinkedList<FileDatabaseRow> result = new LinkedList<FileDatabaseRow>();

        try {
            InputStream inputStream = this.context.openFileInput(this.filename);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            if ((line = reader.readLine()) == null) {
                reader.close();
                throw new InvalidFileDatabaseFormat();
            }

            Pattern indexLinePattern = Pattern.compile("[0-9]+");

            Matcher matcher = indexLinePattern.matcher(line);

            if (!matcher.find()) {
                reader.close();
                throw new InvalidFileDatabaseFormat();
            }

            this.autoIncrement = Integer.parseInt(line);

            while ((line = reader.readLine()) != null) {
                FileDatabaseRow row = FileDatabaseRow.createFromLine(line);

                if (row == null) {
                    reader.close();
                    throw new InvalidFileDatabaseLineException();
                }

                this.rows.put(row.getId(), row);
                result.add(row);
            }
        } catch (FileNotFoundException e) {
            initializeFile(this.filename, this.context);
        } finally {
            if(reader != null) {
                reader.close();
            }
        }

        return result;
    }

    public boolean write() {
        try {
            FileOutputStream outputStream = this.context.openFileOutput(this.filename, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

            writer.append("" + this.autoIncrement);
            writer.newLine();

            for (FileDatabaseRow value : this.rows.values()) {
                writer.append(value.toString());
                writer.newLine();
            }

            writer.close();
            outputStream.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean initializeFile(String filename, Context context) {
        try {
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

            writer.append("" + INITIAL_AUTO_INCREMENT);

            outputStream.close();
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    public FileDatabaseManager add(FileDatabaseRow row) {
        row.setId(this.autoIncrement);

        this.rows.put(this.autoIncrement, row);

        this.autoIncrement++;

        return this;
    }

    public FileDatabaseManager put(FileDatabaseRow row) {
        this.rows.put(row.getId(), row);

        if (row.getId() >= this.autoIncrement) {
            this.autoIncrement = row.getId() + 1;
        }

        return this;
    }

    public FileDatabaseRow get(int id) {
        return this.rows.get(id);
    }

    public int getAutoIncrement() {
        return this.autoIncrement;
    }

    public static boolean validateDatum(String row) {
        Pattern pattern = Pattern.compile("[" + SEPARATOR + "]+");

        Matcher matcher = pattern.matcher(row);

        return !matcher.find();
    }
}
