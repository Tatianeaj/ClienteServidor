package server;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Candidato_VagaDAO {

    public void save(Candidato_Vaga candidatoVaga) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(candidatoVaga);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Candidato_Vaga candidatoVaga) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(candidatoVaga);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Candidato_Vaga candidatoVaga) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(candidatoVaga);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Candidato_Vaga getCandidatoVagaById(int id) {
        Transaction transaction = null;
        Candidato_Vaga candidatoVaga = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            candidatoVaga = session.get(Candidato_Vaga.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return candidatoVaga;
    }

    public Candidato_Vaga getCandidatoVagaByIdCandidato(int idCandidato) {
        Transaction transaction = null;
        Candidato_Vaga candidatoVaga = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            candidatoVaga = session.createQuery("FROM Candidato_Vaga WHERE idCandidato = :idCandidato", Candidato_Vaga.class)
                    .setParameter("idCandidato", idCandidato)
                    .uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return candidatoVaga;
    }

    public Candidato_Vaga getCandidatoVagaByIdVaga(int idVaga) {
        Transaction transaction = null;
        Candidato_Vaga candidatoVaga = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            candidatoVaga = session.createQuery("FROM Candidato_Vaga WHERE idVaga = :idVaga", Candidato_Vaga.class)
                    .setParameter("idVaga", idVaga)
                    .uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return candidatoVaga;
    }



}
