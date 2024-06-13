package server;

//import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class Candidato_CompetenciaDAO {

    public void save(Candidato_Competencia candidatoCompetencia) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(candidatoCompetencia);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
              //  transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Candidato_Competencia candidatoCompetencia) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(candidatoCompetencia);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                //transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Candidato_Competencia candidatoCompetencia) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(candidatoCompetencia);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();

        }
    }

    public Candidato_Competencia getCandidatoCompetenciaById(int id) {
        Transaction transaction = null;
        Candidato_Competencia candidatoCompetencia = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            candidatoCompetencia = session.get(Candidato_Competencia.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
             //   transaction.rollback();
            }
            e.printStackTrace();

        }
        return candidatoCompetencia;
    }

    public Candidato_Competencia getCandidatoCompetenciaByCandidatoId(int id) {
        Transaction transaction = null;
        Candidato_Competencia candidatoCompetencia = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            candidatoCompetencia = session.createQuery("FROM Candidato_Competencia WHERE id_candidato = :id", Candidato_Competencia.class)
                    .setParameter("id", id)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();

        }
        return candidatoCompetencia;
    }

    public Candidato_Competencia getCandidatoCompetenciaByCompetenciaId(int id) {
        Transaction transaction = null;
        Candidato_Competencia candidatoCompetencia = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            candidatoCompetencia = session.createQuery("FROM Candidato_Competencia WHERE id_competencia = :id", Candidato_Competencia.class)
                    .setParameter("id", id)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();

        }
        return candidatoCompetencia;
    }

    public Candidato_Competencia getCandidatoCompetenciaByCandidatoCompetenciaAndExperiencia(Candidato candidato, Competencia competencia, int experiencia) {
        Transaction transaction = null;
        Candidato_Competencia candidatoCompetencia = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Candidato_Competencia WHERE id_candidato = :candidato AND id_competencia = :competencia AND tempo = :experiencia", Candidato_Competencia.class);
            query.setParameter("candidato", candidato);
            query.setParameter("competencia", competencia);
            query.setParameter("experiencia", experiencia);
            candidatoCompetencia = (Candidato_Competencia) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                //transaction.rollback();
            }
            e.printStackTrace();
        }
        return candidatoCompetencia;
    }

    public List<Candidato_Competencia> getCandidatoCompetenciasByCandidato(Candidato candidato) {
        Transaction transaction = null;
        List<Candidato_Competencia> candidatoCompetencias = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            candidatoCompetencias = session.createQuery("FROM Candidato_Competencia WHERE id_candidato = :candidato", Candidato_Competencia.class)
                    .setParameter("candidato", candidato)
                    .list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();
        }
        return candidatoCompetencias;
    }

    public List<Candidato_Competencia> getCandidatoCompetenciasByCandidatoAndCompetencia(Candidato candidato, Competencia competencia) {
        Transaction transaction = null;
        List<Candidato_Competencia> candidatoCompetencias = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Candidato_Competencia WHERE id_candidato = :candidato AND id_competencia = :competencia", Candidato_Competencia.class);
            query.setParameter("candidato", candidato);
            query.setParameter("competencia", competencia);
            candidatoCompetencias = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return candidatoCompetencias;
    }



}