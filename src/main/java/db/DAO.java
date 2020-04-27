package db;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * @author Michael König
 */
public class DAO implements AutoCloseable {

    private final static DAO INSTANCE = new DAO();

    // private Constructor
    private DAO() {
    }

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
        Runner r1 = new Runner("Huber", "Karl", LocalDate.of(1990, 6, 7), 'M', 75);
        Runner r2 = new Runner("Schmidt", "Eva", LocalDate.of(1997, 10, 26), 'W', 55);
        Run l1 = new Run(LocalDate.of(2019, 6, 11), 55, 10350);
        l1.setRunner(r1);
        Run l2 = new Run(LocalDate.of(2019, 6, 12), 172, 42195);
        l2.setRunner(r1);
        Run l3 = new Run(LocalDate.of(2019, 6, 13), 58, 8320);
        l3.setRunner(r2);
        Run l4 = new Run(LocalDate.of(2019, 7, 14), 83, 15000);
        l4.setRunner(r2);
        Run l5 = new Run(LocalDate.of(2019, 7, 15), 115, 21000);
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
        if (i < 0)
            throw new IllegalArgumentException("i must be >= 0");

        EntityManager em = JPAUtil.getEMF().createEntityManager();
        Query q = em.createQuery("select distinct r.runner from Run r left join fetch r.runner.runs where r.distance >= :distance");
        q.setParameter("distance", i);
        List<Runner> result = q.getResultList();
        em.close();
        return result;
    }

    public Runner findRunnerById(int i) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        Query q = em.createQuery("select distinct r from Runner r where r.id = :id");
        q.setParameter("id", i);
        Runner result = (Runner) q.getSingleResult();
        em.close();
        return result;
    }

    public long totalDistance(Runner r, LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate)) throw new IllegalArgumentException("fromDate must be before toDate");
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        Query q = em.createQuery("select sum(run.distance) from Run run where run.runner = :runner and run.date >= :fromDate and run.date <= :toDate");
        q.setParameter("runner", r);
        q.setParameter("fromDate",fromDate);
        q.setParameter("toDate", toDate);
        Long result = (Long) q.getSingleResult();
        em.close();
        return result;
    }

}