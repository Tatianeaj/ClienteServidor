package server;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.UUID;

public class ServidorSocket {

    private static final int PORTA = 22222;
    private static final String URL = "jdbc:mysql://localhost:3306/recrutamento";
    private static final String USUARIO_BD = "root";
    private static final String SENHA_BD = "1234";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(URL, USUARIO_BD, SENHA_BD)) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return;
        }


        try {
            ServerSocket serverSocket = new ServerSocket(PORTA);
            System.out.println("Servidor TCP iniciado na porta " + PORTA);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clienteSocket);

                // Inicia uma thread para lidar com o cliente
                Thread threadCliente = new Thread(new ClienteHandler(clienteSocket));
                threadCliente.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClienteHandler implements Runnable {
        private Socket clienteSocket;

        public ClienteHandler(Socket clienteSocket) {
            this.clienteSocket = clienteSocket;
        }

        private void getCandidato(String email, PrintWriter out) throws SQLException {
            CandidatoDAO candidatoDAO = new CandidatoDAO();
            Candidato candidato = candidatoDAO.getCandidatoByEmail(email);
            System.out.println("Candidato: " + candidato);
            if (candidato != null) {

                JSONObject resposta = new JSONObject();
                resposta.put("operacao", "visualizarCandidato");
                resposta.put("status", (int)201);
                resposta.put("nome", candidato.getNome());
                resposta.put("senha", candidato.getSenha()); // Adicionando a senha ao objeto de resposta
                out.println(resposta.toString());
                System.out.println("Item enviado:" + resposta.toString());

            } else {
                JSONObject resposta = new JSONObject();
                resposta.put("operacao", "visualizarCandidato");
                resposta.put("status", (int)404);
                resposta.put("mensagem", "email não encontrado");
                out.println(resposta.toString());
                System.out.println("Retorno do servidor:" + resposta.toString());

            }
                }

        private void cadCandidato(PrintWriter out) throws SQLException {

            CandidatoDAO candidatoDAO = new CandidatoDAO();

                JSONObject resposta = new JSONObject();
                resposta.put("operacao", "visualizarCandidato");
                resposta.put("status", (int)201);



                out.println(resposta.toString());
                System.out.println("Item enviado:" + resposta.toString());


                out.println(resposta.toString());
                System.out.println("Retorno do servidor:" + resposta.toString());


            }






        @Override
        public void run() {
            try {
                PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));

                String enderecoCliente = clienteSocket.getInetAddress().getHostAddress();
                System.out.println("Cliente IP: " + enderecoCliente);

                String mensagemDoCliente;
                while ((mensagemDoCliente = in.readLine()) != null) {
                    System.out.println("Mensagem recebida do cliente " + enderecoCliente + ": " + mensagemDoCliente);


                    processarMensagem(mensagemDoCliente, out);

                    //if (mensagemDoCliente.equalsIgnoreCase("sair")) {
                      //  System.out.println("Saindo... ");

                        //clienteSocket.close();
                        //break;
                    //}
                }

                clienteSocket.close();
            } catch (IOException | SQLException e) {
                System.out.println("Conexão interrompida pelo cliente.");
            }

        }

        private void processarMensagem(String mensagem, PrintWriter out) throws SQLException, IOException {

            JSONObject json = new JSONObject(mensagem);


            String operacao = json.getString("operacao");

            switch (operacao) {
                case "logout":
                    //OK
                    JSONObject resposta = new JSONObject();
                    resposta.put("operacao", "logoutCandidato");
                    resposta.put("status", (int)204);
                  //  resposta.put("mensagem", "Logout realizado com sucesso");

                    out.println(resposta.toString());
                    System.out.println("Retorno do servidor: " + resposta.toString());
                    //clienteSocket.close();
                    break;
                case "loginCandidato":
                    String email = json.getString("email");
                    String senha = json.getString("senha");

                    CandidatoDAO candidatoDAO = new CandidatoDAO();
                    Candidato candidatoLogado = candidatoDAO.login(email, senha);

                    if (candidatoLogado != null) {

                         resposta = new JSONObject();
                        resposta.put("operacao", "loginCandidato");
                        resposta.put("status", (int)200);
                        resposta.put("token", UUID.randomUUID().toString());
                        //resposta.put("mensagem", "Login realizado com sucesso");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    } else {

                        resposta = new JSONObject();
                        resposta.put("operacao", "loginCandidato");
                        resposta.put("status", (int)401);
                        resposta.put("mensagem", "Login ou senha incorretos");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;
                case "cadastrarCandidato":
                    //OK
                    String nome = json.getString("nome");
                    email = json.getString("email");
                    senha = json.getString("senha");

                    Candidato candidato = new Candidato();
                    candidato.setNome(nome);
                    candidato.setEmail(email);
                    candidato.setSenha(senha);

                    candidatoDAO = new CandidatoDAO();
                    candidatoDAO.save(candidato, out);

                    break;
                case "visualizarCandidato":
                    //OK
                    email = json.getString("email");
                    //senha = json.getString("senha");
                    System.out.println("Visualizar candidato selecionado");
                    getCandidato(email, out);
                    break;
                case "atualizarCandidato":
                    //OK
                     nome = json.getString("nome");
                     email = json.getString("email");
                     senha = json.getString("senha");
                     candidatoDAO = new CandidatoDAO();
                    Candidato candidatoExistente = candidatoDAO.getCandidatoByEmail(email);

                    if (candidatoExistente != null) {
                        candidatoExistente.setNome(nome);
                        candidatoExistente.setEmail(email);
                        candidatoExistente.setSenha(senha);

                        candidatoDAO.update(candidatoExistente, out);
                    } else {
                        // Candidato não encontrado
                        resposta = new JSONObject();
                        resposta.put("operacao", "atualizarCandidato");
                        resposta.put("status", (int)404);
                        resposta.put("mensagem", "Candidato não encontrado");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;
                case "apagarCandidato":
                    //OK
                    email = json.getString("email");
                    candidatoDAO = new CandidatoDAO();
                    Candidato candidatoParaApagar = candidatoDAO.getCandidatoByEmail(email);

                    if (candidatoParaApagar != null) {
                        candidatoDAO.delete(candidatoParaApagar, out);
                    } else {

                        resposta = new JSONObject();
                        resposta.put("operacao", "apagarCandidato");
                        resposta.put("status", (int)404);
                        resposta.put("mensagem", "Candidato não encontrado");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;

                default:
                    System.out.println("Operacao desconhecida");
                    out.println("Operacao desconhecida");
                    break;

            }
        }
        }
    }
