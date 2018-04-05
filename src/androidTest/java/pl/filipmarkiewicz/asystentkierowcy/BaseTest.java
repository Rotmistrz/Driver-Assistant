package pl.filipmarkiewicz.asystentkierowcy;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Filip on 2018-04-04.
 */

public class BaseTest extends TestCase {
    @Test
    public void testGetDateInStandardFormat() throws Exception {
        Calendar calendar = new GregorianCalendar(2018, 2, 21, 23, 6, 1);

        assertEquals("2018-03-21 23:06:01", Base.getDateInStandardFormat(calendar));

        assertEquals("", Base.getDateInStandardFormat(null));
    }

    @Test
    public void testParseDate() throws Exception {
        String sampleDateString = "2018-03-22 22:57:12";
        Calendar sampleCalendar = new GregorianCalendar(2018, 2, 22, 22, 57, 12);

        assertEquals(sampleCalendar, Base.parseDate(sampleDateString));

        assertEquals(Base.parseDate(""), null);
    }

    @Test
    public void testBoolean() throws Exception {
        boolean hop = true;

        String str = Base.parseBoolean(hop);

        assertEquals(str, "1");

        String siup = "0";

        boolean lala = Base.getBooleanOfString(siup);

        assertFalse(lala);
        assertTrue(Base.getBooleanOfString("1"));
    }
}