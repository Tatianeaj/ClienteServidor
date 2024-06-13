package server;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.sql.Connection;


public class VagaDAO {

    private Connection conn;

    public VagaDAO(Connection conn) {
        this.conn = conn;
    }

    public void save(Vaga vaga) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(vaga);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
              //  transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Vaga vaga) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(vaga);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Vaga vaga) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(vaga);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    public Vaga getVagaById(int id) {
        Transaction transaction = null;
        Vaga vaga = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            vaga = session.get(Vaga.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
              //  transaction.rollback();
            }
            e.printStackTrace();
        }
        return vaga;
    }

    public List<Vaga> getAllVagas() {
        Transaction transaction = null;
        List<Vaga> vagas = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            vagas = session.createQuery("from Vaga", Vaga.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                //transaction.rollback();
            }
            e.printStackTrace();
        }
        return vagas;
    }

}

