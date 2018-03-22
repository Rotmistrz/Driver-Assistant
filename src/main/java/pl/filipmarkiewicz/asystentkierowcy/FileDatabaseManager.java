package pl.filipmarkiewicz.asystentkierowcy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Filip on 2018-03-22.
 */

public class FileDatabaseManager {
    public final static String separator = "|";

    private String path;
    private LinkedList<FileDatabaseItem> dataList;

    private BufferedReader reader;
    private boolean isOpen = false;

    public FileDatabaseManager(String path) {
        this.path = path;
        this.dataList = new LinkedList<FileDatabaseItem>();
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean open() {
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
    }

    public boolean save() {
        return true;
    }

    public String fetch() throws IOException {
        String line = reader.readLine();

        return line;
    }
}
