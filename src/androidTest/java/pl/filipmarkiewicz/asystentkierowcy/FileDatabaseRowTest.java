package pl.filipmarkiewicz.asystentkierowcy;

/**
 * Created by Filip on 2018-03-25.
 */

import android.content.Context;

import junit.framework.TestCase;

import org.junit.Test;

import android.support.test.InstrumentationRegistry;

import java.io.File;
import java.util.LinkedList;

import pl.filipmarkiewicz.filedatabase.FileDatabaseManager;
import pl.filipmarkiewicz.filedatabase.FileDatabaseRow;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Filip on 2018-03-21.
 */

public class FileDatabaseRowTest extends TestCase {
    @Test
    public void testCreating() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        int id = 1;

        String patternLine = id + "|40.98|180.23|2018-03-24 15:31:12";

        String[] sampleData = {
                "40.98",
                "180.23",
                "2018-03-24 15:31:12"
        };

        FileDatabaseRow patternRow = new FileDatabaseRow(id, sampleData);
        FileDatabaseRow row = FileDatabaseRow.createFromLine(patternLine);

        String line = patternRow.toString();

        assertEquals(patternRow.getId(), row.getId());
        assertArrayEquals(patternRow.getData(), row.getData());
        assertEquals(patternLine, line);
    }

    @Test
    public void testValidate() throws Exception {
        assertTrue(FileDatabaseRow.validateDatum("Hop siup sialala"));
        assertFalse(FileDatabaseRow.validateDatum("Hop siup sialala|bumcykcyk"));
    }

    @Test
    public void testEquals() throws Exception {
        String[] data = {
                "Hop",
                "Siup",
                "1",
                "1.45"
        };

        String[] data02 = {
                "Hop",
                "Siup",
                "1",
                "1.45"
        };

        String[] data03 = {
                "Hop",
                "Siup",
                "0",
                "1.46"
        };

        FileDatabaseRow row = new FileDatabaseRow(1, data);
        FileDatabaseRow row02 = new FileDatabaseRow(1, data02);

        FileDatabaseRow row03 = new FileDatabaseRow(1, data03);

        assertEquals(row, row02);
        assertNotEquals(row, row03);
    }
}