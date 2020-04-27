package db;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

/**
 * @author Michael König
 */
public class DAO implements AutoCloseable{

    private final static DAO INSTANCE = new DAO();

    // private Constructor
    private DAO() {}

    public static DAO getINSTANCE() {
        return INSTANCE;
    }

    public boolean persist(Run run) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(run.getRunner());
            em.persist(run);
            tx.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public boolean insertTestData() {
        Runner r1 = new Runner("Huber", "Karl", LocalDate.of(1990,6,7), 'M', 75);
        Runner r2 = new Runner("Schmidt", "Eva", LocalDate.of(1997,10,26), 'W', 55);
        Run l1 = new Run(LocalDate.of(2019,6,11), 55, 10350);
        l1.setRunner(r1);
        Run l2 = new Run(LocalDate.of(2019,6,12), 172, 42195);
        l2.setRunner(r1);
        Run l3 = new Run(LocalDate.of(2019,6,13), 58, 8320);
        l3.setRunner(r2);
        Run l4 = new Run(LocalDate.of(2019,7,14), 83, 15000);
        l4.setRunner(r2);
        Run l5 = new Run(LocalDate.of(2019,7,15), 115, 21000);
        l5.setRunner(r2);

        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(l1);
            em.persist(l2);
            em.persist(l3);
            em.persist(l4);
            em.persist(l5);
            tx.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public void close() {
        JPAUtil.close();
    }

    public List<Runner> longRun(int i) {
        throw new UnsupportedOperationException();
    }

    public Runner findRunnerById(int i) {
        throw new UnsupportedOperationException();
    }

    public int totalDistance(Runner r, LocalDate of, LocalDate of1) {
        throw new UnsupportedOperationException();
    }
}