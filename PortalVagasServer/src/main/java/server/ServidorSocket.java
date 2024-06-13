package server;
import org.json.JSONArray;
import org.json.JSONObject;
//import com.google.gson.JsonArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.List;
import java.util.UUID;

public class ServidorSocket {

    private static final int PORTA = 22222;
    private static final String URL = "jdbc:mysql://localhost:3306/recrutamento";
    private static final String USUARIO_BD = "root";
    private static final String SENHA_BD = "1234";

    public static void main(String[] args) {


        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USUARIO_BD, SENHA_BD);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso.");

            ServerSocket serverSocket = new ServerSocket(PORTA);
            System.out.println("Servidor TCP iniciado na porta " + PORTA);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clienteSocket);

                // Inicia uma thread para lidar com o cliente
                Thread threadCliente = new Thread(new ClienteHandler(clienteSocket, conn));
                threadCliente.start();
            }
        } catch (SQLException | IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }


        try {
            ServerSocket serverSocket = new ServerSocket(PORTA);
            System.out.println("Servidor TCP iniciado na porta " + PORTA);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clienteSocket);

                // Inicia uma thread para lidar com o cliente
                Thread threadCliente = new Thread(new ClienteHandler(clienteSocket, conn));
                threadCliente.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClienteHandler implements Runnable {
        private Socket clienteSocket;
        private Connection conn;

        public ClienteHandler(Socket clienteSocket, Connection conn) {
            this.clienteSocket = clienteSocket;
            this.conn = conn;
        }

        private void getCandidato(String email, PrintWriter out) throws SQLException {
            CandidatoDAO candidatoDAO = new CandidatoDAO(conn);
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

            CandidatoDAO candidatoDAO = new CandidatoDAO(conn);

                JSONObject resposta = new JSONObject();
                resposta.put("operacao", "visualizarCandidato");
                resposta.put("status", (int)201);



                out.println(resposta.toString());
                System.out.println("Item enviado:" + resposta.toString());


                out.println(resposta.toString());
                System.out.println("Retorno do servidor:" + resposta.toString());


            }
//////////////////////////////////////////////////////////////////////


        private void getEmpresa(String email, PrintWriter out) throws SQLException {
            EmpresaDAO empresaDAO = new EmpresaDAO(conn);
            Empresa empresa = empresaDAO.getEmpresaByEmail(email);
            System.out.println("Empresa: " + empresa);
            if (empresa != null) {

                JSONObject resposta = new JSONObject();
                resposta.put("operacao", "visualizarEmpresa");
                resposta.put("status", (int)201);
                resposta.put("razaoSocial", empresa.getRazaoSocial());
                resposta.put("cnpj", empresa.getCnpj()); // Adicionando a senha ao objeto de resposta
                resposta.put("senha", empresa.getSenha());
                resposta.put("descricao", empresa.getDescricao());
                resposta.put("ramo", empresa.getRamo());// Adicionando a senha ao objeto de resposta
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

        private void cadEmpresa(PrintWriter out) throws SQLException {

            EmpresaDAO empresaDAO = new EmpresaDAO(conn);

            JSONObject resposta = new JSONObject();
            resposta.put("operacao", "visualizarEmpresa");
            resposta.put("status", (int)201);



            out.println(resposta.toString());
            System.out.println("Item enviado:" + resposta.toString());


            out.println(resposta.toString());
            System.out.println("Retorno do servidor:" + resposta.toString());


        }







///////////////////////////////////////////////////////////////////
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

                   
                  
                }

                clienteSocket.close();
            } catch (IOException | SQLException e) {
                System.out.println("Conexão interrompida pelo cliente.");
            }finally {
                try {
                    if (clienteSocket != null) {
                        clienteSocket.close();
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o socket do cliente.");
                }
            }

        }

        private void processarMensagem(String mensagem, PrintWriter out) throws SQLException, IOException {

            JSONObject json = new JSONObject(mensagem);


            String operacao = json.getString("operacao");

            switch (operacao) {
                case "logout":
                    //OK
                    JSONObject resposta = new JSONObject();
                    resposta.put("operacao", "logout");
                    resposta.put("status", (int) 204);
                    //  resposta.put("mensagem", "Logout realizado com sucesso");

                    out.println(resposta.toString());
                    System.out.println("Retorno do servidor: " + resposta.toString());
                    //clienteSocket.close();
                    break;

                case "loginCandidato":
                    String email = json.getString("email");
                    String senha = json.getString("senha");

                    CandidatoDAO candidatoDAO = new CandidatoDAO(conn);
                    Candidato candidatoLogado = candidatoDAO.login(email, senha);

                    if (candidatoLogado != null) {

                        resposta = new JSONObject();
                        resposta.put("operacao", "loginCandidato");
                        resposta.put("status", (int) 200);
                        resposta.put("token", UUID.randomUUID().toString());
                        //resposta.put("mensagem", "Login realizado com sucesso");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    } else {

                        resposta = new JSONObject();
                        resposta.put("operacao", "loginCandidato");
                        resposta.put("status", (int) 401);
                        resposta.put("mensagem", "Login ou senha incorretos");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;
                ///////////////////////
                case "loginEmpresa":
                    email = json.getString("email");
                    senha = json.getString("senha");

                    EmpresaDAO empresaDAO = new EmpresaDAO(conn);
                    Empresa empresaLogada = empresaDAO.login(email, senha);

                    if (empresaLogada != null) {

                        resposta = new JSONObject();
                        resposta.put("operacao", "loginEmpresa");
                        resposta.put("status", (int) 200);
                        resposta.put("token", UUID.randomUUID().toString());
                        //resposta.put("mensagem", "Login realizado com sucesso");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    } else {

                        resposta = new JSONObject();
                        resposta.put("operacao", "loginEmpresa");
                        resposta.put("status", (int) 401);
                        resposta.put("mensagem", "Login ou senha incorretos");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;

                //////////////
                case "listarCandidatos":
                    candidatoDAO = new CandidatoDAO(conn);
                    candidatoDAO.listarCandidatos(out);
                    break;

                case "visualizarCompetenciaExperiencia":
                    String emailCandidato = json.getString("email");

                    candidatoDAO = new CandidatoDAO(conn);
                    Candidato candidato = candidatoDAO.getCandidatoByEmail(emailCandidato);

                    if (candidato != null) {
                        Candidato_CompetenciaDAO candidatoCompetenciaDAO = new Candidato_CompetenciaDAO();
                        List<Candidato_Competencia> candidatoCompetencias = candidatoCompetenciaDAO.getCandidatoCompetenciasByCandidato(candidato);

                        JSONArray competenciasExperiencias = new JSONArray();
                        for (Candidato_Competencia candidatoCompetencia : candidatoCompetencias) {
                            JSONObject competenciaExperiencia = new JSONObject();
                            competenciaExperiencia.put("competencia", candidatoCompetencia.getIdCompetencia().getCompetencia());
                            competenciaExperiencia.put("experiencia", candidatoCompetencia.getTempo());
                            competenciasExperiencias.put(competenciaExperiencia);
                        }

                        resposta = new JSONObject();
                        resposta.put("operacao", "visualizarCompetenciaExperiencia");
                        resposta.put("competenciaExperiencia", competenciasExperiencias);
                        resposta.put("status", 201);

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    } else {
                        resposta = new JSONObject();
                        resposta.put("operacao", "visualizarCompetenciaExperiencia");
                        resposta.put("status", 422);
                        resposta.put("mensagem", "");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;


                case "cadastrarCandidato":
                    //OK
                    String nome = json.getString("nome");
                    email = json.getString("email");
                    senha = json.getString("senha");

                    candidato = new Candidato();
                    candidato.setNome(nome);
                    candidato.setEmail(email);
                    candidato.setSenha(senha);

                    candidatoDAO = new CandidatoDAO(conn);
                    candidatoDAO.save(candidato, out);

                    break;

                /////////////
                case "cadastrarEmpresa":
                    //OK
                    String razaoSocial = json.getString("razaoSocial");
                    email = json.getString("email");
                    String cnpj = json.getString("cnpj");
                    senha = json.getString("senha");
                    String descricao = json.getString("descricao");
                    String ramo = json.getString("ramo");

                    Empresa empresa = new Empresa();
                    empresa.setRazaoSocial(razaoSocial);
                    empresa.setEmail(email);
                    empresa.setCnpj(cnpj);
                    empresa.setSenha(senha);
                    empresa.setDescricao(descricao);
                    empresa.setRamo(ramo);

                    empresaDAO = new EmpresaDAO(conn);
                    empresaDAO.save(empresa, out);

                    break;
                case "apagarCompetenciaExperiencia":
                    emailCandidato = json.getString("email");
                    JSONArray competenciasExperiencias = json.getJSONArray("competenciaExperiencia");

                     candidatoDAO = new CandidatoDAO(conn);
                     candidato = candidatoDAO.getCandidatoByEmail(emailCandidato);

                    if (candidato != null) {
                        CompetenciaDAO competenciaDAO = new CompetenciaDAO();
                        Candidato_CompetenciaDAO candidatoCompetenciaDAO = new Candidato_CompetenciaDAO();

                        for (int i = 0; i < competenciasExperiencias.length(); i++) {
                            JSONObject competenciaExperiencia = competenciasExperiencias.getJSONObject(i);
                            String competencia = competenciaExperiencia.getString("competencia");

                            Competencia competenciaObj = competenciaDAO.getCompetenciaByName(competencia);

                            if (competenciaObj != null) {
                                List<Candidato_Competencia> candidatoCompetencias = candidatoCompetenciaDAO.getCandidatoCompetenciasByCandidatoAndCompetencia(candidato, competenciaObj);

                                for (Candidato_Competencia candidatoCompetencia : candidatoCompetencias) {
                                    candidatoCompetenciaDAO.delete(candidatoCompetencia);
                                }
                            } else {
                                resposta = new JSONObject();
                                resposta.put("operacao", "apagarCompetenciaExperiencia");
                                resposta.put("status", 422);
                                resposta.put("mensagem", "Competência " + competencia + " não encontrada");

                                out.println(resposta.toString());
                                System.out.println("Retorno do servidor: " + resposta.toString());
                            }
                        }
                    } else {
                         resposta = new JSONObject();
                        resposta.put("operacao", "apagarCompetenciaExperiencia");
                        resposta.put("status", 422);
                        resposta.put("mensagem", "Candidato não encontrado");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }

                    resposta = new JSONObject();
                    resposta.put("operacao", "apagarCompetenciaExperiencia");
                    resposta.put("status", 201);
                    resposta.put("mensagem", "Competencia/Experiencia apagada com sucesso");

                    out.println(resposta.toString());
                    System.out.println("Retorno do servidor: " + resposta.toString());


                    break;
// 10.20.8.143 testado

                case "apagarVaga":
                    int idVaga = json.getInt("idVaga");
                    VagaDAO vagaDAO = new VagaDAO(conn);
                    Vaga vagaParaApagar = vagaDAO.getVagaById(idVaga);

                    if (vagaParaApagar != null) {
                        vagaDAO.delete(vagaParaApagar);

                        resposta = new JSONObject();
                        resposta.put("operacao", "apagarVaga");
                        resposta.put("status", 201);
                        resposta.put("mensagem", "Vaga apagada com sucesso");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    } else {
                        resposta = new JSONObject();
                        resposta.put("operacao", "apagarVaga");
                        resposta.put("status", 422);
                        resposta.put("mensagem", "Vaga não encontrada");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;


                case "cadastrarVaga":
                    nome = json.getString("nome");
                    String emailEmpresa = json.getString("email");
                    int faixaSalarial = json.getInt("faixaSalarial");
                    descricao = json.getString("descricao");
                    String estado = json.getString("estado");
                    JSONArray competencias = json.getJSONArray("competencias");

                    empresaDAO = new EmpresaDAO(conn);
                    empresa = empresaDAO.getEmpresaByEmail(emailEmpresa);

                    if (empresa != null) {
                        Vaga vaga = new Vaga();
                        vaga.setNomeVaga(nome);
                        vaga.setIdEmpresa(empresa);
                        vaga.setFaixaSalarial(faixaSalarial);
                        vaga.setDescricao(descricao);
                        vaga.setEstado(estado);

                        vagaDAO = new VagaDAO(conn);
                        vagaDAO.save(vaga);

                        CompetenciaDAO competenciaDAO = new CompetenciaDAO();
                        Vaga_CompetenciaDAO vagaCompetenciaDAO = new Vaga_CompetenciaDAO(conn);

                        for (int i = 0; i < competencias.length(); i++) {
                            Object competenciaObj = competencias.get(i);
                            if (competenciaObj instanceof String) {
                                String competencia = (String) competenciaObj;
                                // Processa a competência como String
                                // Resto do seu código...
                            } else if (competenciaObj instanceof Integer) {
                                int competencia = competencias.getInt(i);
                                // Processa a competência como int
                                // Resto do seu código...
                            } else {
                                System.out.println("Skipping value in competencias array: " + competenciaObj);
                            }
                        }

                        resposta = new JSONObject();
                        resposta.put("operacao", "cadastrarVaga");
                        resposta.put("status", 201);
                        resposta.put("mensagem", "Vaga cadastrada com sucesso");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    } else {
                        resposta = new JSONObject();
                        resposta.put("operacao", "cadastrarVaga");
                        resposta.put("status", 422);
                        resposta.put("mensagem", "Empresa não encontrada");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;


                case "listarVagas":
                    vagaDAO = new VagaDAO(conn);
                    List<Vaga> vagas = vagaDAO.getAllVagas();

                    JSONArray vagasJsonArray = new JSONArray();
                    for (Vaga vaga : vagas) {
                        JSONObject vagaJson = new JSONObject();
                        vagaJson.put("id", vaga.getIdVaga());
                        vagaJson.put("nome", vaga.getNomeVaga());
                        vagaJson.put("faixaSalarial", vaga.getFaixaSalarial());
                        vagaJson.put("descricao", vaga.getDescricao());
                        vagaJson.put("estado", vaga.getEstado());
                        vagaJson.put("idEmpresa", vaga.getIdEmpresa().getId());
                        vagasJsonArray.put(vagaJson);
                    }

                    resposta = new JSONObject();
                    resposta.put("operacao", "listarVagas");
                    resposta.put("status", 201);
                    resposta.put("vagas", vagasJsonArray);

                    out.println(resposta.toString());
                    System.out.println("Retorno do servidor: " + resposta.toString());
                    break;


                case "cadastrarCompetenciaExperiencia":
                     competenciasExperiencias = json.getJSONArray("competenciaExperiencia");
                     emailCandidato = json.getString("email");

                     candidatoDAO = new CandidatoDAO(conn);
                    candidato = candidatoDAO.getCandidatoByEmail(emailCandidato);

                    if (candidato != null) {
                        CompetenciaDAO competenciaDAO = new CompetenciaDAO();
                        Candidato_CompetenciaDAO candidatoCompetenciaDAO = new Candidato_CompetenciaDAO();

                        for (int i = 0; i < competenciasExperiencias.length(); i++) {
                            JSONObject competenciaExperiencia = competenciasExperiencias.getJSONObject(i);
                            String competencia = competenciaExperiencia.getString("competencia");
                            int experiencia = competenciaExperiencia.getInt("experiencia");
                            //int experienciaInt = Integer.parseInt(experiencia);

                            Competencia competenciaObj = competenciaDAO.getCompetenciaByName(competencia);

                            if (competenciaObj != null) {
                                Candidato_Competencia candidatoCompetencia = new Candidato_Competencia();
                                candidatoCompetencia.setIdCandidato(candidato);
                                candidatoCompetencia.setIdCompetencia(competenciaObj);
                                candidatoCompetencia.setTempo(experiencia);

                                candidatoCompetenciaDAO.save(candidatoCompetencia);
                            } else {
                                 resposta = new JSONObject();
                                resposta.put("operacao", "cadastrarCompetenciaExperiencia");
                                resposta.put("status", (int)422);
                                resposta.put("mensagem", "");

                                out.println(resposta.toString());
                                System.out.println("Retorno do servidor: " + resposta.toString());
                            }
                        }
                    } else {
                     resposta = new JSONObject();
                        resposta.put("operacao", "cadastrarCompetenciaExperiencia");
                        resposta.put("status", (int)422);
                        resposta.put("mensagem", "");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    // Adicione este bloco de código para enviar uma resposta de sucesso
                    resposta = new JSONObject();
                    resposta.put("operacao", "cadastrarCompetenciaExperiencia");
                    resposta.put("status", 201); // Código de status HTTP para "Criado"
                    resposta.put("mensagem", "Competência/Experiência cadastradas com sucesso");
                    out.println(resposta.toString());
                    System.out.println("Retorno do servidor: " + resposta.toString());
                    break;
                //////////////
                case "visualizarCandidato":
                    //OK
                    email = json.getString("email");
                    //senha = json.getString("senha");
                    System.out.println("Visualizar candidato selecionado");
                    getCandidato(email, out);
                    break;

                    ////////////////
                case "visualizarEmpresa":
                email = json.getString("email");
                //senha = json.getString("senha");
                System.out.println("Visualizar empresa selecionada");
                getEmpresa(email, out);
                break;

                /////////////////

                case "atualizarVaga":
                     idVaga = json.getInt("idVaga");
                     nome = json.getString("nome");
                     emailEmpresa = json.getString("email");
                    faixaSalarial = json.getInt("faixaSalarial");
                     descricao = json.getString("descricao");
                    estado = json.getString("estado");
                     competencias = json.getJSONArray("competencias");

                     empresaDAO = new EmpresaDAO(conn);
                     empresa = empresaDAO.getEmpresaByEmail(emailEmpresa);

                    if (empresa != null) {
                        vagaDAO = new VagaDAO(conn);
                        Vaga vaga = vagaDAO.getVagaById(idVaga);

                        if (vaga != null) {
                            vaga.setNomeVaga(nome);
                            vaga.setIdEmpresa(empresa);
                            vaga.setFaixaSalarial(faixaSalarial);
                            vaga.setDescricao(descricao);
                            vaga.setEstado(estado);

                            vagaDAO.update(vaga);

                            CompetenciaDAO competenciaDAO = new CompetenciaDAO();
                            Vaga_CompetenciaDAO vagaCompetenciaDAO = new Vaga_CompetenciaDAO(conn);

                            // Remove all existing competencies for the job
                            List<Vaga_Competencia> existingCompetencies = vagaCompetenciaDAO.getVagaCompetenciasByVaga(vaga);
                            for (Vaga_Competencia existingCompetency : existingCompetencies) {
                                vagaCompetenciaDAO.delete(existingCompetency);
                            }

                            // Add new competencies
                            for (int i = 0; i < competencias.length(); i++) {
                                String competencia = competencias.getString(i);
                                Competencia competenciaObj = competenciaDAO.getCompetenciaByName(competencia);

                                if (competenciaObj != null) {
                                    Vaga_Competencia vagaCompetencia = new Vaga_Competencia();
                                    vagaCompetencia.setIdVaga(vaga);
                                    vagaCompetencia.setIdCompetencia(competenciaObj);

                                    vagaCompetenciaDAO.save(vagaCompetencia);
                                }
                            }

                             resposta = new JSONObject();
                            resposta.put("operacao", "atualizarVaga");
                            resposta.put("status", 201);
                            resposta.put("mensagem", "Vaga atualizada com sucesso");

                            out.println(resposta.toString());
                            System.out.println("Retorno do servidor: " + resposta.toString());
                        } else {
                             resposta = new JSONObject();
                            resposta.put("operacao", "atualizarVaga");
                            resposta.put("status", 422);
                            resposta.put("mensagem", "Vaga não encontrada");

                            out.println(resposta.toString());
                            System.out.println("Retorno do servidor: " + resposta.toString());
                        }
                    } else {
                         resposta = new JSONObject();
                        resposta.put("operacao", "atualizarVaga");
                        resposta.put("status", 422);
                        resposta.put("mensagem", "Empresa não encontrada");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;



                case "atualizarCandidato":
                    //OK
                     nome = json.getString("nome");
                     email = json.getString("email");
                     senha = json.getString("senha");
                     candidatoDAO = new CandidatoDAO(conn);
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
                    ////////////////

                case "visualizarVaga":
                     idVaga = json.getInt("idVaga");
                    emailEmpresa = json.getString("email");

                    empresaDAO = new EmpresaDAO(conn);
                     empresa = empresaDAO.getEmpresaByEmail(emailEmpresa);

                    if (empresa != null) {
                      vagaDAO = new VagaDAO(conn);
                        Vaga vaga = vagaDAO.getVagaById(idVaga);

                        if (vaga != null) {
                            Vaga_CompetenciaDAO vagaCompetenciaDAO = new Vaga_CompetenciaDAO(conn);
                            List<Vaga_Competencia> vagaCompetencias = vagaCompetenciaDAO.getVagaCompetenciasByVaga(vaga);

                            JSONArray competenciasJsonArray = new JSONArray();
                            for (Vaga_Competencia vagaCompetencia : vagaCompetencias) {
                                JSONObject competenciaJson = new JSONObject();
                                competenciaJson.put("competencia", vagaCompetencia.getIdCompetencia().getCompetencia());
                                competenciaJson.put("tempo", vagaCompetencia.getTempo());
                                competenciasJsonArray.put(competenciaJson);
                            }

                             resposta = new JSONObject();
                            resposta.put("operacao", "visualizarVaga");
                            resposta.put("status", 201);
                            //resposta.put("nome", vaga.getNomeVaga());
                            resposta.put("faixaSalarial", vaga.getFaixaSalarial());
                            resposta.put("descricao", vaga.getDescricao());
                            resposta.put("estado", vaga.getEstado());
                            resposta.put("competencias", competenciasJsonArray);

                            out.println(resposta.toString());
                            System.out.println("Retorno do servidor: " + resposta.toString());
                        } else {
                             resposta = new JSONObject();
                            resposta.put("operacao", "visualizarVaga");
                            resposta.put("status", 422);
                            resposta.put("mensagem", "");

                            out.println(resposta.toString());
                            System.out.println("Retorno do servidor: " + resposta.toString());
                        }
                    } else {
                        resposta = new JSONObject();
                        resposta.put("operacao", "visualizarVaga");
                        resposta.put("status", 422);
                        resposta.put("mensagem", "");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;


                case "atualizarEmpresa":

                    email = json.getString("email");
                    razaoSocial = json.getString("razaoSocial");
                    cnpj = json.getString("cnpj");
                    senha = json.getString("senha");
                    descricao = json.getString("descricao");
                    ramo = json.getString("ramo");
                    empresaDAO = new EmpresaDAO(conn);
                    Empresa empresaExistente = EmpresaDAO.getEmpresaByEmail(email);

                    if (empresaExistente != null) {
                        empresaExistente.setRazaoSocial(razaoSocial);
                        empresaExistente.setEmail(email);
                        empresaExistente.setCnpj(cnpj);
                        empresaExistente.setSenha(senha);
                        empresaExistente.setDescricao(descricao);
                        empresaExistente.setRamo(ramo);

                        empresaDAO.update(empresaExistente, out);

                    } else {
                        // Candidato não encontrado
                        resposta = new JSONObject();
                        resposta.put("operacao", "atualizarEmpresa");
                        resposta.put("status", (int)404);
                        resposta.put("mensagem", "Email não encontrado");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;

                case "atualizarCompetenciaExperiencia":
                    emailCandidato = json.getString("email");
                     competenciasExperiencias = json.getJSONArray("competenciaExperiencia");

                     candidatoDAO = new CandidatoDAO(conn);
                    candidato = candidatoDAO.getCandidatoByEmail(emailCandidato);

                    if (candidato != null) {
                        CompetenciaDAO competenciaDAO = new CompetenciaDAO();
                        Candidato_CompetenciaDAO candidatoCompetenciaDAO = new Candidato_CompetenciaDAO();

                        for (int i = 0; i < competenciasExperiencias.length(); i++) {
                            JSONObject competenciaExperiencia = competenciasExperiencias.getJSONObject(i);
                            String competencia = competenciaExperiencia.getString("competencia");
                            int experiencia = competenciaExperiencia.getInt("experiencia");

                            Competencia competenciaObj = competenciaDAO.getCompetenciaByName(competencia);

                            if (competenciaObj != null) {
                                List<Candidato_Competencia> candidatoCompetencias = candidatoCompetenciaDAO.getCandidatoCompetenciasByCandidatoAndCompetencia(candidato, competenciaObj);

                                if (!candidatoCompetencias.isEmpty()) {
                                    for (Candidato_Competencia candidatoCompetencia : candidatoCompetencias) {
                                        candidatoCompetencia.setTempo(experiencia);
                                        candidatoCompetenciaDAO.update(candidatoCompetencia);
                                    }
                                } else {
                                     resposta = new JSONObject();
                                    resposta.put("operacao", "atualizarCompetenciaExperiencia");
                                    resposta.put("status", 422);
                                    resposta.put("mensagem", "Competência " + competencia + " não encontrada para o candidato");

                                    out.println(resposta.toString());
                                    System.out.println("Retorno do servidor: " + resposta.toString());
                                }
                            } else {
                                 resposta = new JSONObject();
                                resposta.put("operacao", "atualizarCompetenciaExperiencia");
                                resposta.put("status", 422);
                                resposta.put("mensagem", "Competência " + competencia + " não encontrada");

                                out.println(resposta.toString());
                                System.out.println("Retorno do servidor: " + resposta.toString());
                            }
                        }
                    } else {
                         resposta = new JSONObject();
                        resposta.put("operacao", "atualizarCompetenciaExperiencia");
                        resposta.put("status", 422);
                        resposta.put("mensagem", "Candidato não encontrado");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }

                    resposta = new JSONObject();
                    resposta.put("operacao", "atualizarCompetenciaExperiencia");
                    resposta.put("status", 201);
                    resposta.put("mensagem", "Competencia/Experiencia atualizada com sucesso");

                    out.println(resposta.toString());
                    System.out.println("Retorno do servidor: " + resposta.toString());


                    break;

                    ////////////////
                case "apagarCandidato":
                    //OK
                    email = json.getString("email");
                    candidatoDAO = new CandidatoDAO(conn);
                    Candidato candidatoParaApagar = candidatoDAO.getCandidatoByEmail(email);

                    if (candidatoParaApagar != null) {
                        candidatoDAO.delete(candidatoParaApagar, out);
                    } else {

                        resposta = new JSONObject();
                        resposta.put("operacao", "apagarCandidato");
                        resposta.put("status", (int)404);
                        resposta.put("mensagem", "E-mail não encontrado");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;
                    //////////////
                case "apagarEmpresa":
                    //OK
                    email = json.getString("email");
                    empresaDAO = new EmpresaDAO(conn);
                    Empresa empresaParaApagar = EmpresaDAO.getEmpresaByEmail(email);

                    if (empresaParaApagar != null) {
                        empresaDAO.delete(empresaParaApagar, out);
                    } else {

                        resposta = new JSONObject();
                        resposta.put("operacao", "apagarEmpresa");
                        resposta.put("status", (int)404);
                        resposta.put("mensagem", "E-mail não encontrado");

                        out.println(resposta.toString());
                        System.out.println("Retorno do servidor: " + resposta.toString());
                    }
                    break;


                /////////////

                default:
                    System.out.println("Operacao desconhecida");
                    out.println("Operacao desconhecida");
                    break;

            }
        }
        }
    }
