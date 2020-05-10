package db;

import model.Runner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Michael König
 */
public class DAOTest {
    private static DAO dao;

    @BeforeAll
    static void beforeAll() {
        dao = DAO.getINSTANCE();
        dao.insertTestData();
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        System.out.println(em.createQuery("select r from Runner r").getResultList());
        System.out.println(em.createQuery("select r from Run r").getResultList());
    }

    // Testfall: testet DAO.longRun(25000)
    // Liefert eine Liste mit der L¨aufer Huber Karl
    @Test
    void testLongRun1() {
        List<Runner> runner = dao.longRun(25000);
        assertEquals(1, runner.size());
        assertEquals("Huber", runner.get(0).getLastName());
    }

    // Testfall: testet DAO.longRun(10000)
    // Liefert eine Liste mit zwei L¨aufern
    @Test
    void testLongRun2() {
        List<Runner> runner = dao.longRun(10000);
        assertEquals(2, runner.size());
    }

    // Testfall: testet DAO.longRun(50000)
    // Liefert eine leere Liste
    @Test
    void testLongRun3() {
        List<Runner> runner = dao.longRun(50000);
        assertTrue(runner.isEmpty());
    }

    // Testfall: testet DAO.longRun(-10000)
    // Erwartetes Ergebnis: wirft eine IllegalArgumentException
    @Test
    void testLongRun4() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.longRun(-10000));
    }

    // Testfall: liefert die Gesamtstrecke des Läufers mit der ID 2 im Juli 2019
    // Erwartetes Ergebnis: 36000
    @Test
    void testTotalDistance1() {
        Runner r = dao.findRunnerById(2);
        System.out.println(r);
        assertEquals("Schmidt", r.getLastName());
        long distance = dao.totalDistance(r, LocalDate.of(2019, 7, 1),
                LocalDate.of(2019, 7, 31));
        assertEquals(36000, distance);
    }

    // Testfall: Ruft totalDistance mit dem Runner null auf
    // Erwartetes Ergebnis: IllegalArgumentException
    @Test
    void testTotalDistance2() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.totalDistance(null, LocalDate.of(2019, 7, 1),
                        LocalDate.of(2019, 7, 31)));

    }

    // Testfall: Ruft totalDistance mit einem fromDate auf, das nach dem afterDate liegt
    // Erwartetes Ergebnis: IllegalArgumentException
    @Test
    void testTotalDistance3() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.totalDistance(new Runner(), LocalDate.of(2020, 1, 1),
                        LocalDate.of(2019, 1, 1)));

    }

    // Testfall: liefert die Gesamtstrecke des Läufers mit der ID 1 im Jahr 2019
    // Erwartetes Ergebnis: 52545
    @Test
    void testTotalDistance4() {
        Runner r = dao.findRunnerById(1);
        assertEquals("Huber", r.getLastName());
        long distance = dao.totalDistance(r, LocalDate.of(2019, 1, 1),
                LocalDate.of(2020, 1, 1));
        assertEquals(10350+42195, distance);
    }


}
