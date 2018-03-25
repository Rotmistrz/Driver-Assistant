package pl.filipmarkiewicz.asystentkierowcy;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Filip on 2018-03-22.
 */

public class FileDatabaseManager {
    public final static String SEPARATOR = "|";

    private Context context;
    private String filename;
    private LinkedList<String> lines;

    private BufferedReader reader;
    private boolean isOpen = false;

    public FileDatabaseManager(String filename, Context context) {
        this.context = context;
        this.filename = filename;
        this.lines = new LinkedList<String>();
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

    public LinkedList<String> read() throws IOException {
        this.lines = null;
        this.lines = new LinkedList<String>();

        try {
            InputStream inputStream = this.context.openFileInput(this.filename);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                this.lines.add(line);
            }

            reader.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            File file = new File(this.context.getFilesDir(), this.filename);
        }

        return this.lines;
    }

    public boolean write() {
        try {
            FileOutputStream outputStream = this.context.openFileOutput(this.filename, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

            for (String line : this.lines) {
                writer.append(line);
                writer.newLine();
            }

            writer.close();
            outputStream.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public FileDatabaseManager add(String line) {
        this.lines.add(line);

        return this;
    }

    public FileDatabaseManager addAll(LinkedList<String> lines) {
        this.lines = lines;

        return this;
    }

    public boolean save() {
        return true;
    }

    public String fetch() throws IOException {
        return reader.readLine();
    }

    public static boolean validateLine(String str) {
        Pattern pattern = Pattern.compile("[" + SEPARATOR + "]+");

        Matcher matcher = pattern.matcher(str);

        return !matcher.find();
    }
}
