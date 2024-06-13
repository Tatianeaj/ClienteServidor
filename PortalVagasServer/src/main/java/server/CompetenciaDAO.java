package server;


import org.hibernate.Session;
import org.hibernate.Transaction;


public class CompetenciaDAO {

    public void save(Competencia competencia) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(competencia);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Competencia competencia) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(competencia);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Competencia competencia) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(competencia);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
              //  transaction.rollback();
            }
            e.printStackTrace();

        }
    }

    public Competencia getCompetenciaById(int id) {
        Transaction transaction = null;
        Competencia competencia = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            competencia = session.get(Competencia.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();

        }
        return competencia;
    }

    public Competencia getCompetenciaByName(String competencia) {
        Transaction transaction = null;
        Competencia competencia1 = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            competencia1 = session.createQuery("FROM Competencia WHERE competencia = :competencia", Competencia.class)
                    .setParameter("competencia", competencia)
                    .uniqueResult();

            // Se a competência não existir, crie uma nova e salve-a
            if (competencia1 == null) {
                competencia1 = new Competencia();
                competencia1.setCompetencia(competencia);
                session.save(competencia1);
                transaction.commit();
            }

        } catch (Exception e) {
            if (transaction != null) {
                // transaction.rollback();
            }
            e.printStackTrace();
        }
        return competencia1;
    }
}


