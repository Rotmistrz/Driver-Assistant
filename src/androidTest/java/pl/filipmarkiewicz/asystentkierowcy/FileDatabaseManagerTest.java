package pl.filipmarkiewicz.asystentkierowcy;

/**
 * Created by Filip on 2018-03-25.
 */

import android.content.Context;

import junit.framework.TestCase;

import org.junit.Test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Test;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Filip on 2018-03-21.
 */

public class FileDatabaseManagerTest extends TestCase {
    @Test
    public void testWritingReading() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        final String filename = "testfile.txt";
        String firstLine = "40.98|180.23|2018-03-24 15:31:12";
        String secondLine = "20.23|98.24|2018-03-12 11:21:46";

        FileDatabaseManager fdm = new FileDatabaseManager(filename, context);
        fdm.add(firstLine);
        fdm.add(secondLine);

        assertTrue(fdm.write());

        FileDatabaseManager anotherFdm = new FileDatabaseManager(filename, context);
        LinkedList<String> list = anotherFdm.read();

        assertEquals(list.get(0), firstLine);
        assertEquals(list.get(1), secondLine);

        File file = new File(context.getFilesDir(), filename);
        assertTrue(file.delete());
    }

    @Test
    public void testValidate() throws Exception {
        assertTrue(FileDatabaseManager.validateLine("Hop siup sialala"));
        assertFalse(FileDatabaseManager.validateLine("Hop siup sialala|bumcykcyk"));
    }
}