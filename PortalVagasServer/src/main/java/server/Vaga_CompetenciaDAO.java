package server;

//import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.util.List;

public class Vaga_CompetenciaDAO {

    private Connection connection;

    public Vaga_CompetenciaDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(Vaga_Competencia vagaCompetencia) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(vagaCompetencia);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                //transaction.rollback();
            }
            e.printStackTrace();

        }
    }

    public void update(Vaga_Competencia vagaCompetencia) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(vagaCompetencia);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                //transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Vaga_Competencia vagaCompetencia) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(vagaCompetencia);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Vaga_Competencia getVagaCompetenciaById(int id) {
        Transaction transaction = null;
        Vaga_Competencia vagaCompetencia = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            vagaCompetencia = session.get(Vaga_Competencia.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();
        }
        return vagaCompetencia;
    }

    public Vaga_Competencia getVagaCompetenciaByVagaId(int id) {
        Transaction transaction = null;
        Vaga_Competencia vagaCompetencia = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            vagaCompetencia = session.get(Vaga_Competencia.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();
        }
        return vagaCompetencia;
    }

    public List<Vaga_Competencia> getVagaCompetenciasByVaga(Vaga vaga) {
        Transaction transaction = null;
        List<Vaga_Competencia> vagaCompetencias = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Vaga_Competencia WHERE id_vaga = :vaga", Vaga_Competencia.class);
            query.setParameter("vaga", vaga);
            vagaCompetencias = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return vagaCompetencias;
    }

    public Vaga_Competencia getVagaCompetenciaByCompetenciaId(int id) {
        Transaction transaction = null;
        Vaga_Competencia vagaCompetencia = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            vagaCompetencia = session.get(Vaga_Competencia.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();
        }
        return vagaCompetencia;
    }




}
