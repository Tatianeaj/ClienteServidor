package server;
import server.Empresa;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.UUID;

public class EmpresaDAO {

    public static Empresa getEmpresaByEmail(String email) {
        Transaction transaction = null;
        Empresa empresa = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            empresa = session.createQuery("FROM Empresa WHERE email = :email", Empresa.class)
                    .setParameter("email", email)
                    .uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return empresa;
    }

    public Empresa getEmpresaByCNPJ(String cnpj) {
        Transaction transaction = null;
        Empresa empresa = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            empresa = session.createQuery("FROM Empresa WHERE cnpj = :cnpj", Empresa.class)
                    .setParameter("cnpj", cnpj)
                    .uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return empresa;
    }


    public void save(Empresa empresa, PrintWriter out) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Empresa empresaExistente = getEmpresaByEmail(empresa.getEmail());
            if (empresaExistente != null) {

                JSONObject resposta = new JSONObject();
                resposta.put("operacao", "cadastrarEmpresa");
                resposta.put("status", (int) 422);
                resposta.put("mensagem", "Email já cadastrado");


                out.println(resposta.toString());
                System.out.println("Resposta enviada ao cliente: " + resposta.toString());
                return;
            }
            empresaExistente = getEmpresaByCNPJ(empresa.getCnpj());
            if (empresaExistente != null) {

                JSONObject resposta = new JSONObject();
                resposta.put("operacao", "cadastrarEmpresa");
                resposta.put("status", (int) 422);
                resposta.put("mensagem", "CNPJ já cadastrado");


                out.println(resposta.toString());
                System.out.println("Resposta enviada ao cliente: " + resposta.toString());
                return;
            }

            session.persist(empresa);

            transaction.commit();


            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "cadastrarEmpresa");
            resposta.put("token", UUID.randomUUID().toString());
            resposta.put("status", (int) 201);
            //  resposta.put("mensagem", "Candidato cadastrado com sucesso");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());

        } catch (Exception e) {
            if (transaction != null) {
               // transaction.rollback();
            }
            e.printStackTrace();


            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "cadastrarEmpresa");
            resposta.put("status", (int) 422);
            resposta.put("mensagem", "");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());
        }
    }

    public void update(Empresa empresa, PrintWriter out) {
        Transaction transaction = null;
        Session session = null;

        session = HibernateUtil.getSessionFactory().openSession();

        transaction = session.beginTransaction();


        Empresa empresaExistente = getEmpresaByEmail(empresa.getEmail());
        if (empresaExistente == null) {

            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "atualizarEmpresa");
            resposta.put("status", (int) 404);
            resposta.put("mensagem", "Email não encontrado");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());
            return;
        }


        session.update(empresa);

        transaction.commit();


        JSONObject resposta = new JSONObject();
        resposta.put("operacao", "atualizarEmpresa");
        resposta.put("status", (int)201);
        // resposta.put("mensagem", "Candidato atualizado com sucesso");


        out.println(resposta.toString());
        System.out.println("Resposta enviada ao cliente: " + resposta.toString());


    }
    public void delete(Empresa empresa, PrintWriter out) {
        if (empresa == null) {

            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "apagarEmpresa");
            resposta.put("status", (int)404);
            resposta.put("mensagem", "E-mail não encontrado");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());
            return;
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.delete(empresa);

            transaction.commit();


            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "apagarEmpresa");
            resposta.put("status", (int)201);
            // resposta.put("mensagem", "Candidato apagado com sucesso");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "apagarEmpresa");
            resposta.put("status", (int)500);
            resposta.put("mensagem", "Erro ao apagar empresa");


            out.println(resposta.toString());
            System.out.println("Resposta enviada ao cliente: " + resposta.toString());
        }
    }

    public Empresa login(String email, String senha) {
        Transaction transaction = null;
        Empresa empresa = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            empresa = (Empresa) session.createQuery("FROM Empresa WHERE email = :email AND senha = :senha")
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
        return empresa;
    }

}

