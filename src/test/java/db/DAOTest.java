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

}
