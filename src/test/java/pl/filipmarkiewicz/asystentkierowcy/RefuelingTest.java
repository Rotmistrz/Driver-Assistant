package pl.filipmarkiewicz.asystentkierowcy;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by Filip on 2018-03-21.
 */

public class RefuelingTest extends TestCase {
    @Test
    public void testGetDateInStandardFormat() throws Exception {
        Calendar calendar = new GregorianCalendar(2018, 2, 21, 23, 6, 1);

        assertEquals("2018-03-21 23:06:01", Refueling.getDateInStandardFormat(calendar));
    }

    @Test
    public void testParseDate() throws Exception {
        String sampleDateString = "2018-03-22 22:57:12";
        Calendar sampleCalendar = new GregorianCalendar(2018, 2, 22, 22, 57, 12);

        assertEquals(sampleCalendar, Refueling.parseDate(sampleDateString));
    }

    @Test
    public void testCreateFromFileLine() {
        String line = "40.98|180.23|2018-03-24 15:31:12";

        double amount = 40.98;
        double price = 180.23;
        Calendar calendar = new GregorianCalendar(2018, 2, 24, 15, 31, 12);

        Refueling refueling = Refueling.createFromFileLine(line);

        assertEquals(amount, refueling.getAmount());
        assertEquals(price, refueling.getPrice());
        assertEquals(calendar, refueling.getDate());
    }
}
