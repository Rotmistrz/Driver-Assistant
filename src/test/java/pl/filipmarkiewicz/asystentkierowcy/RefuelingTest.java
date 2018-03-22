package pl.filipmarkiewicz.asystentkierowcy;

import junit.framework.TestCase;

import org.junit.Test;

import java.text.SimpleDateFormat;
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
}
