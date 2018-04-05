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
    public void testToFileDatabaseRow() {
        Repair repair = new Repair(1, false, "Hop siup", 120.34);
        repair.setFinalPrice(140);
        repair.setDoneDate(new GregorianCalendar(2018, 3, 4, 20, 3, 4));

        String[] data = {
                "0",
                "Hop siup",
                "120.34",
                "2018-04-04 20:03:04",
                "140.0",
                ""
        };

        FileDatabaseRow row = new FileDatabaseRow(1, data);

        FileDatabaseRow createdRow = repair.toFileDatabaseRow();

        assertEquals(row, createdRow);
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
        Context context = InstrumentationRegistry.getTargetContext();

        FileDatabaseManager fdm = new FileDatabaseManager("testfile-02.txt", context);

        Repair firstTestRepair = new Repair(1, false, "Wymiana opon", 130);

        Repair secondTestRepair = new Repair(2, false, "Naprawa tylnych hamulców", 250.34);

        Repair thirdTestRepair = new Repair(3, false, "Wymiana uszczelki na kielichach", 125.15);

        fdm.add(firstTestRepair.toFileDatabaseRow());
        fdm.add(secondTestRepair.toFileDatabaseRow());

        assertTrue(fdm.write());

        assertEquals(fdm.getAutoIncrement(), 3);

        assertTrue(thirdTestRepair.save(fdm));

        assertEquals(thirdTestRepair.getId(), 3);

        assertEquals(fdm.getAutoIncrement(), 4);

        try {
            LinkedList<FileDatabaseRow> rows = fdm.read();

            FileDatabaseRow firstRow = rows.get(firstTestRepair.getId() - 1);
            FileDatabaseRow secondRow = rows.get(secondTestRepair.getId() - 1);
            FileDatabaseRow thirdRow = rows.get(thirdTestRepair.getId() - 1);

            Repair first = Repair.createFromFileDatabaseRow(firstRow);
            Repair second = Repair.createFromFileDatabaseRow(secondRow);
            Repair third = Repair.createFromFileDatabaseRow(thirdRow);

            assertEquals(first, firstTestRepair);
            assertEquals(second, secondTestRepair);
            assertEquals(third, thirdTestRepair);

            boolean oldIsDone = secondTestRepair.isDone();
            double oldExpectedPrice = secondTestRepair.getExpectedPrice();
            String oldName = secondTestRepair.getName();
            double oldFinalPrice = secondTestRepair.getFinalPrice();

            secondTestRepair.setDone(true);
            secondTestRepair.setName("Zmieniono nazwę");
            secondTestRepair.setExpectedPrice(280.12);
            secondTestRepair.setFinalPrice(380.90);

            assertTrue(secondTestRepair.save(fdm));

            rows = null;
            rows = fdm.read();

            firstRow = rows.get(firstTestRepair.getId() - 1);
            secondRow = rows.get(secondTestRepair.getId() - 1);
            thirdRow = rows.get(thirdTestRepair.getId() - 1);

            first = Repair.createFromFileDatabaseRow(firstRow);
            second = Repair.createFromFileDatabaseRow(secondRow);
            third = Repair.createFromFileDatabaseRow(thirdRow);

            assertEquals(first, firstTestRepair);
            assertEquals(second, secondTestRepair);
            assertEquals(third, thirdTestRepair);

            assertNotEquals(second.isDone(), oldIsDone);
            assertNotEquals(second.getExpectedPrice(), oldExpectedPrice);
            assertNotEquals(second.getName(), oldName);
            assertNotEquals(second.getFinalPrice(), oldFinalPrice);
        } catch (Exception e) {
            fail("Failure during reading the file." + e);
        }
    }
}
