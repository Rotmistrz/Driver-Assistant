package pl.filipmarkiewicz.asystentkierowcy;

/**
 * Created by Filip on 2018-03-21.
 */

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import pl.filipmarkiewicz.filedatabase.FileDatabaseManager;
import pl.filipmarkiewicz.filedatabase.FileDatabaseRow;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import pl.filipmarkiewicz.filedatabase.FileDatabaseRow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


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
    public void testCreateFromFileDatabaseRow() {
        int id = 1;
        String[] data = {
                "3",
                "40.98",
                "180.23",
                "2018-03-24 15:31:12"
        };

        FileDatabaseRow row = new FileDatabaseRow(id, data);

        Refueling refueling = Refueling.createFromFileDatabaseRow(row);
        Refueling pattern = new Refueling(1, FuelType.DIESEL_VERVA, 40.98, 180.23, new GregorianCalendar(2018, 2, 24, 15, 31, 12));

        assertEquals(refueling, pattern);
    }

    @Test
    public void testEquals() {
        Refueling refueling = new Refueling(1, FuelType.DIESEL_VERVA, 40.98, 180.23, new GregorianCalendar(2018, 2, 24, 15, 31, 12));
        Refueling another = new Refueling(1, FuelType.DIESEL_VERVA, 40.98, 180.23, new GregorianCalendar(2018, 2, 24, 15, 31, 12));
        Refueling other = new Refueling(1, FuelType.DIESEL_PLAIN, 40.98, 180.23, new GregorianCalendar(2018, 2, 24, 15, 12, 23));

        assertTrue(refueling.equals(another));
        assertFalse(refueling.equals(other));
    }

    @Test
    public void testSave() {
        Context context = InstrumentationRegistry.getTargetContext();

        FileDatabaseManager fdm = new FileDatabaseManager("testfile.txt", context);

        Refueling firstTestRefueling = new Refueling(1, FuelType.DIESEL_VERVA, 30.45, 120.34,
                new GregorianCalendar(2018, 2, 27, 11, 2, 8));

        Refueling secondTestRefueling = new Refueling(2, FuelType.DIESEL_PLAIN, 20.45, 100.34,
                new GregorianCalendar(2018, 2, 31, 17, 8, 12));

        Refueling thirdTestRefueling = new Refueling(3, FuelType.DIESEL_VERVA, 25.15, 110.10,
                new GregorianCalendar(2018, 3, 4, 12, 18, 21));

        fdm.add(firstTestRefueling.toFileDatabaseRow());
        fdm.add(secondTestRefueling.toFileDatabaseRow());

        assertTrue(fdm.write());

        assertEquals(fdm.getAutoIncrement(), 3);

        assertTrue(thirdTestRefueling.save(fdm));

        assertEquals(thirdTestRefueling.getId(), 3);

        assertEquals(fdm.getAutoIncrement(), 4);

        try {
            LinkedList<FileDatabaseRow> rows = fdm.read();

            FileDatabaseRow firstRow = rows.get(firstTestRefueling.getId() - 1);
            FileDatabaseRow secondRow = rows.get(secondTestRefueling.getId() - 1);
            FileDatabaseRow thirdRow = rows.get(thirdTestRefueling.getId() - 1);

            Refueling first = Refueling.createFromFileDatabaseRow(firstRow);
            Refueling second = Refueling.createFromFileDatabaseRow(secondRow);
            Refueling third = Refueling.createFromFileDatabaseRow(thirdRow);

            assertEquals(first, firstTestRefueling);
            assertEquals(second, secondTestRefueling);
            assertEquals(third, thirdTestRefueling);

            FuelType oldFuelType = secondTestRefueling.getFuelType();
            double oldAmount = secondTestRefueling.getAmount();
            double oldPrice = secondTestRefueling.getPrice();
            Calendar oldDate = secondTestRefueling.getDate();

            secondTestRefueling.setFuelType(FuelType.DIESEL_VERVA);
            secondTestRefueling.setAmount(23.45);
            secondTestRefueling.setPrice(112.34);
            secondTestRefueling.setDate(new GregorianCalendar(2018, 2, 30, 16, 12, 1));

            assertTrue(secondTestRefueling.save(fdm));

            rows = null;
            rows = fdm.read();

            firstRow = rows.get(firstTestRefueling.getId() - 1);
            secondRow = rows.get(secondTestRefueling.getId() - 1);
            thirdRow = rows.get(thirdTestRefueling.getId() - 1);

            first = Refueling.createFromFileDatabaseRow(firstRow);
            second = Refueling.createFromFileDatabaseRow(secondRow);
            third = Refueling.createFromFileDatabaseRow(thirdRow);

            assertEquals(first, firstTestRefueling);
            assertEquals(second, secondTestRefueling);
            assertEquals(third, thirdTestRefueling);

            assertNotEquals(second.getFuelType(), oldFuelType);
            assertNotEquals(second.getAmount(), oldAmount);
            assertNotEquals(second.getPrice(), oldPrice);
            assertNotEquals(second.getDate(), oldDate);
        } catch (Exception e) {
            fail("Failure during reading the file.");
        }
    }
}