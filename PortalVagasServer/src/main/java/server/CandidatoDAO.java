package server;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.UUID;

public class CandidatoDAO {

    public Candidato getCandidatoByEmail(String email) {
        Transaction transaction = null;
        Candidato candidato = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            candidato = session.createQuery("FROM Candidato WHERE email = :email", Candidato.class)
                    .setParameter("email", email)
                    .uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return candidato;
    }

    public void save(Candidato candidato, PrintWriter out) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Candidato candidatoExistente = getCandidatoByEmail(candidato.getEmail());
            if (candidatoExistente != null) {

                JSONObject resposta = new JSONObject();
                resposta.put("operacao", "cadastrarCandidato");
                resposta.put("status", (int) 422);
                resposta.put("mensagem", "Email já cadastrado");


                out.println(resposta.toString());
                System.out.println("Resposta enviada ao cliente: " + resposta.toString());
                return;
            }


            session.persist(candidato);

            transaction.commit();


            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "cadastrarCandidato");
            resposta.put("token", UUID.randomUUID().toString());
            resposta.put("status", (int) 201);
            resposta.put("mensagem", "Candidato cadastrado com sucesso");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();


            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "cadastrarCandidato");
            resposta.put("status", (int) 404);
            resposta.put("mensagem", "");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());
        }
    }

    public void update(Candidato candidato, PrintWriter out) {
        Transaction transaction = null;
        Session session = null;

            session = HibernateUtil.getSessionFactory().openSession();

            transaction = session.beginTransaction();


            Candidato candidatoExistente = getCandidatoByEmail(candidato.getEmail());
            if (candidatoExistente == null) {

                JSONObject resposta = new JSONObject();
                resposta.put("operacao", "atualizarCandidato");
                resposta.put("status", (int) 404);
                resposta.put("mensagem", "Email não encontrado");


                out.println(resposta.toString());
                System.out.println("Resposta enviada ao cliente: " + resposta.toString());
                return;
            }


            session.update(candidato);

            transaction.commit();


            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "atualizarCandidato");
            resposta.put("status", (int)201);
            resposta.put("mensagem", "Candidato atualizado com sucesso");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());


        }
    public void delete(Candidato candidato, PrintWriter out) {
        if (candidato == null) {

            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "apagarCandidato");
            resposta.put("status", (int)404);
            resposta.put("mensagem", "Candidato não encontrado");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());
            return;
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.delete(candidato);

            transaction.commit();


            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "apagarCandidato");
            resposta.put("status", (int)201);
            resposta.put("mensagem", "Candidato apagado com sucesso");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "apagarCandidato");
            resposta.put("status", (int)500);
            resposta.put("mensagem", "Erro ao apagar candidato");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());
        }
    }

    public Candidato login(String email, String senha) {
        Transaction transaction = null;
        Candidato candidato = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            candidato = (Candidato) session.createQuery("FROM Candidato C WHERE C.email = :email AND C.senha = :senha")
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .uniqueResult();
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return candidato;
    }
    }

