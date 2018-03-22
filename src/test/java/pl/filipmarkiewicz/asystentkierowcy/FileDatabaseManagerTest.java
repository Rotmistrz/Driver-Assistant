package pl.filipmarkiewicz.asystentkierowcy;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by Filip on 2018-03-21.
 */

public class FileDatabaseManagerTest extends TestCase {
    @Test
    public void testValidate() throws Exception {
        assertTrue(FileDatabaseManager.validate("Hop siup sialala"));
        assertFalse(FileDatabaseManager.validate("Hop siup sialala|bumcykcyk"));
    }
}