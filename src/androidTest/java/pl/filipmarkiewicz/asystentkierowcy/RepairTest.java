package pl.filipmarkiewicz.asystentkierowcy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import pl.filipmarkiewicz.filedatabase.FileDatabaseManager;
import pl.filipmarkiewicz.filedatabase.FileDatabaseRow;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by Filip on 2018-04-04.
 */

public class RepairTest extends TestCase {

    @Test
    public void testCreateFromFileDatabaseRow() {
        int id = 1;
        String[] data = {
                "1",
                "Wymiana okładzin w tylnych hamulcach",
                "150",
                "2018-03-24 15:31:12",
                "180.67",
                "Strasznie piszczą hamulce tylne. Prawdopodobnnie wykładziny są już strasznie wyjechane."
        };

        FileDatabaseRow row = new FileDatabaseRow(id, data);

        Repair repair = Repair.createFromFileDatabaseRow(row);

        Repair pattern = new Repair(1, true, "Wymiana okładzin w tylnych hamulcach", 150);
        pattern.setDoneDate(new GregorianCalendar(2018, 2, 24, 15, 31, 12));
        pattern.setFinalPrice(180.67);
        pattern.setDescription("Strasznie piszczą hamulce tylne. Prawdopodobnnie wykładziny są już strasznie wyjechane.");

        assertEquals(repair, pattern);
    }

    @Test
    public void testEquals() {
        Repair repair = new Repair(1, true, "Wymiana okładzin w tylnych hamulcach", 150);
        repair.setDoneDate(new GregorianCalendar(2018, 2, 24, 15, 31, 12));
        repair.setFinalPrice(180.67);
        repair.setDescription("Strasznie piszczą hamulce tylne. Prawdopodobnnie wykładziny są już strasznie wyjechane.");

        Repair pattern = new Repair(1, true, "Wymiana okładzin w tylnych hamulcach", 150);
        pattern.setDoneDate(new GregorianCalendar(2018, 2, 24, 15, 31, 12));
        pattern.setFinalPrice(180.67);
        pattern.setDescription("Strasznie piszczą hamulce tylne. Prawdopodobnnie wykładziny są już strasznie wyjechane.");

        Repair wrongPattern = new Repair(1, false, "Wymiana okładzin w tylnych hamulcach.", 150);
        wrongPattern.setDoneDate(new GregorianCalendar(2018, 2, 24, 15, 31, 12));
        wrongPattern.setFinalPrice(180.67);
        wrongPattern.setDescription("Strasznie piszczą hamulce tylne. Prawdopodobnnie wykładziny są już strasznie wyjechane.");

        assertTrue(repair.equals(pattern));
        assertFalse(repair.equals(wrongPattern));
    }

    @Test
    public void testSave() {
        /**Context context = InstrumentationRegistry.getTargetContext();

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
        }**/
    }
}
