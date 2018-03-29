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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Filip on 2018-03-21.
 */

public class FileDatabaseManagerTest extends TestCase {
    @Test
    public void testWritingReading() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        final String filename = "testfile.txt";
        String firstLine = "1|40.98|180.23|2018-03-24 15:31:12";
        String secondLine = "2|20.23|98.24|2018-03-12 11:21:46";

        FileDatabaseRow first = FileDatabaseRow.createFromLine(firstLine);
        FileDatabaseRow second = FileDatabaseRow.createFromLine(secondLine);

        FileDatabaseManager fdm = new FileDatabaseManager(filename, context);
        fdm.put(first);
        fdm.put(second);

        assertTrue(fdm.write());

        FileDatabaseManager anotherFdm = new FileDatabaseManager(filename, context);
        LinkedList<FileDatabaseRow> list = anotherFdm.read();

        assertEquals(anotherFdm.getAutoIncrement(), 3);

        FileDatabaseRow firstResult = list.get(0);
        FileDatabaseRow secondResult = list.get(1);

        assertEquals(firstResult.getId(), first.getId());
        assertArrayEquals(firstResult.getData(), first.getData());

        assertEquals(secondResult.getId(), second.getId());
        assertArrayEquals(secondResult.getData(), second.getData());

        File file = new File(context.getFilesDir(), filename);
        assertTrue(file.delete());
    }

    @Test
    public void testAutoIncrement() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        final String filename = "testfile.txt";

        String[] data = {
                "hop",
                "siup",
                "sialala"
        };

        FileDatabaseRow first = new FileDatabaseRow(1, data);


        FileDatabaseManager fdm = new FileDatabaseManager(filename, context);
        fdm.add(first);

        assertEquals(fdm.getAutoIncrement(), 2);

        FileDatabaseRow second = new FileDatabaseRow(10, data);
        fdm.put(second);

        assertEquals(fdm.getAutoIncrement(), 11);
    }

    @Test
    public void testValidate() throws Exception {
        //assertTrue(FileDatabaseManager.validateDatum("Hop siup sialala"));
        //assertFalse(FileDatabaseManager.validateDatum("Hop siup sialala|bumcykcyk"));
    }
}