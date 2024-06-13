package vagas.portalvagasclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class Main {
    private static volatile boolean isLoggedIn = false;


    public static void menu(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
        String userType;
        do {
            System.out.println("Digite o tipo de usuário (1 para candidato, 2 para empresa):");
            userType = teclado.readLine();
        } while (!userType.equals("1") && !userType.equals("2"));

        String opcao;
        if (userType.equals("1")) {
            try {
                System.out.println("Escolha [1] para fazer login ou [2] para se cadastrar como candidato:");
                opcao = teclado.readLine();
                switch (opcao) {
                    case "1":
                        loginCandidato(saida, teclado, entrada);
                        break;
                    case "2":
                        cadastrarCandidato(saida, teclado, entrada);
                        break;
                    default:
                        System.out.println("Opção inválida. Digite novamente.");
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
                System.out.println("Reiniciando o menu..." );
                menu(saida, teclado, entrada);
            }
        } else if (userType.equals("2")) {
            try {
                System.out.println("Escolha [1] para fazer login ou [2] para se cadastrar como empresa:");
                opcao = teclado.readLine();
                switch (opcao) {
                    case "1":
                        loginEmpresa(saida, teclado, entrada);
                        break;
                    case "2":
                        cadastrarEmpresa(saida, teclado, entrada);
                        break;
                    default:
                        System.out.println("Opção inválida. Digite novamente.");
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
                System.out.println("Reiniciando o menu...");
            }
        }
    }



    public static void menuCandidato(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
        while (isLoggedIn)
        {

            // System.out.println("Conectado ao servidor. Digite uma opção:");
            System.out.println("1. Meus Dados(Consultar candidato)");
            System.out.println("2. Cadastrar competencias/experiências");
            System.out.println("3. Consultar minhas competencias/experiências");
            System.out.println("4. Atualizar minhas competencias/experiências");
            System.out.println("5. Deletar minhas competencias/experiências");
            System.out.println("6. Alterar meus dados(Atualizar candidato)");
            System.out.println("7. Deletar meu cadastro(Apagar candidato)");
            System.out.println("8. Pesquisa filtrada");
            System.out.println("9. Logout ");
            //System.out.println("7. Sair");

            String opcao = teclado.readLine();
            while ((opcao = teclado.readLine()) != null) {
                switch (opcao) {
                    case "1":
                        visualizarCandidato(saida, teclado, entrada);
                        break;
                    case "2":
                      
                        cadastrarCompetenciaExperiencia(saida, teclado, entrada);
                        break;
                    case "3":
                        visualizarCompetenciaExperiencia(saida, teclado, entrada);
                        break;
                   
                    case "4":
                        atualizarCompetenciaExperiencia(saida, teclado, entrada);
                        break;
                
                    case "5":
                        apagarCompetenciaExperiencia(saida, teclado, entrada);
                        
                        break;
                    case "6":
                        atualizarCandidato(saida, teclado, entrada);
                      
                        break;
                    case "7":
                        apagarCandidato(saida, teclado, entrada);
                       
                        break;
                    case "8":
                        listarVagas(saida, teclado, entrada);
                        break;
                    case "9":
                        isLoggedIn = false;
                        
                        logout(saida, entrada);

                        break;

                    default:
                        System.out.println("Opção inválida. Digite novamente.");
                }
            }
        }
    }





    public static void menuEmpresa(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
     while (isLoggedIn) {
         //aqui
         System.out.println("1. Meus Dados(Consultar empresa)");
         System.out.println("2. Cadastrar vagas");
         System.out.println("3. Consultar minhas vagas");
         System.out.println("4. Atualizar minhas vagas");
         System.out.println("5. Deletar minhas vagas");
         System.out.println("6. Alterar meus dados(Atualizar empresa)");
         System.out.println("7. Deletar meu cadastro(Apagar empresa)");
         System.out.println("8. Listar Vagas");
         System.out.println("9. Pesquisa filtrada");
         System.out.println("10. Logout");

         String opcao = teclado.readLine();
         while ((opcao = teclado.readLine()) != null) {
             switch (opcao) {
                 case "1":
                     visualizarEmpresa(saida, teclado, entrada);
                     break;
                 case "2":
                     
                     cadastrarVaga(saida, teclado, entrada);
                     break;
                 case "3":
                     
                     visualizarVaga(saida, teclado, entrada);
                     break;
                 case "4":
                    
                     atualizarVaga(saida, teclado, entrada);
                     break;
                 case "5":
                    
                     apagarVaga(saida, teclado, entrada);
                     break;
                 case "6":
                     atualizarEmpresa(saida, teclado, entrada);
                     break;
                 case "7":
                     apagarEmpresa(saida, teclado, entrada);
                     break;
                 case "8":
                     listarVagas(saida, teclado, entrada);
                     break;
                 case "9":
                     filtrarVagas(saida, teclado, entrada);
                     break;
                 case "10":
                     isLoggedIn = false;
                    
                     logout(saida, entrada);
                     break;
                 default:
                     System.out.println("Opção inválida. Digite novamente.");
             }

         }


     }
    }



    public static void main(String[] args) {
        BufferedReader tc = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Digite o IP do servidor:");
        String IP_SERVIDOR = null;
        try {
            IP_SERVIDOR = tc.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao ler o IP do servidor. Saindo...");
            System.exit(1);
        }
        final int PORTA_SERVIDOR = 22222;


        try (Socket socket = new Socket(IP_SERVIDOR, PORTA_SERVIDOR);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {
            String userType;
            do {
                System.out.println("Digite o tipo de usuário (1 para candidato, 2 para empresa):");
                userType = teclado.readLine();
            } while (!userType.equals("1") && !userType.equals("2"));

            String opcao;
            if (userType.equals("1")) {
                try {
                    System.out.println("Escolha [1] para fazer login ou [2] para se cadastrar como candidato:");
                   
                    opcao = teclado.readLine();
                    switch (opcao) {
                        case "1":
                            loginCandidato(saida, teclado, entrada);
                            break;
                        case "2":
                            cadastrarCandidato(saida, teclado, entrada);
                            break;
                        default:
                            System.out.println("Opção inválida. Digite novamente.");
                    }
                }catch (Exception e) {
                    System.out.println("Ocorreu um erro: " + e.getMessage());
                    System.out.println("Reiniciando o menu...");
                }


            } else if (userType.equals("2")) {
                try {
                    System.out.println("Escolha [1] para fazer login ou [2] para se cadastrar como empresa:");
                    
                    opcao = teclado.readLine();
                    switch (opcao) {
                        case "1":
                            loginEmpresa(saida, teclado, entrada);
                            break;
                        case "2":
                            cadastrarEmpresa(saida, teclado, entrada);
                            break;
                        default:
                            System.out.println("Opção inválida. Digite novamente.");
                            //    }
                    }


                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    System.out.println("Ocorreu um erro: " + e.getMessage());
                    System.out.println("Reiniciando o menu...");
                }
            }
                } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
      
    }



    private static String criarMensagemJSON(String... args) {
        JsonObject json = new JsonObject();
        for (int i = 0; i < args.length; i += 2) {
            json.addProperty(args[i], args[i + 1]);
        }
        return json.toString();
    }


    private static void cadastrarCandidato(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
        System.out.println("Digite o nome de usuário:");
        String nome = teclado.readLine();
        System.out.println("Digite o email do usuário:");
        String email = teclado.readLine();
        System.out.println("Digite a senha:");
        String senha = teclado.readLine();
        System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "cadastrarCandidato", "nome", nome, "email", email, "senha", senha));
        saida.println(criarMensagemJSON("operacao", "cadastrarCandidato", "nome", nome, "email", email, "senha", senha));
        saida.flush();

        String resposta = entrada.readLine();
        System.out.println("Resposta do servidor: " + resposta);

        JsonObject respostaJson = JsonParser.parseString(resposta).getAsJsonObject();
        String status = respostaJson.get("status").getAsString();
        if ("201".equals(status)) {
           
            loginCandidato(saida, teclado, entrada);
        } else {
            System.out.println("Erro ao cadastrar candidato." + respostaJson.get("mensagem").getAsString());
        }

    }


    private static void cadastrarEmpresa(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
        System.out.println("Digite a razão social:");
        String razaoSocial = teclado.readLine();
        System.out.println("Digite o email da empresa:");
        String email = teclado.readLine();
        System.out.println("Digite o cnpj:");
        String cnpj = teclado.readLine();
        System.out.println("Digite a senha:");
        String senha = teclado.readLine();
        System.out.println("Digite a descricao:");
        String descricao = teclado.readLine();
        System.out.println("Digite o ramo:");
        String ramo = teclado.readLine();


        saida.println(criarMensagemJSON("operacao", "cadastrarEmpresa",
                "razaoSocial", razaoSocial,
                "email", email, "cnpj", cnpj, "senha", senha,
                "descricao", descricao, "ramo", ramo));
        System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "cadastrarEmpresa",
                "razaoSocial", razaoSocial,
                "email", email, "cnpj", cnpj, "senha", senha,
                "descricao", descricao, "ramo", ramo));
        saida.flush();

        String resposta = entrada.readLine();
        System.out.println("Resposta do servidor: " + resposta);

        JsonObject respostaJson = JsonParser.parseString(resposta).getAsJsonObject();
        String status = respostaJson.get("status").getAsString();
        if ("201".equals(status)) {
           
            loginEmpresa(saida, teclado, entrada);
        } else {
            System.out.println("Erro ao cadastrar empresa." + respostaJson.get("mensagem").getAsString());
        }

    }


    private static void cadastrarCompetenciaExperiencia(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
       
        String email = UserSession.getInstance(null, null).getEmail();
        String token = UserSession.getInstance(null, null).getToken();

        JsonArray competenciaExperienciaArray = new JsonArray();
        String continuar;

        do {
            JsonObject competenciaExperiencia = new JsonObject();
            System.out.println("Digite a competencia:");
            String competencia = teclado.readLine();
            competenciaExperiencia.addProperty("competencia", competencia);

            System.out.println("Digite a experiencia:");
            int experiencia = Integer.parseInt(teclado.readLine());
            competenciaExperiencia.addProperty("experiencia", experiencia);

            competenciaExperienciaArray.add(competenciaExperiencia);

            System.out.println("Deseja adicionar outra competencia? (s/n)");
            continuar = teclado.readLine();
        } while ("s".equalsIgnoreCase(continuar));

        JsonObject json = new JsonObject();
        json.addProperty("operacao", "cadastrarCompetenciaExperiencia");
        json.addProperty("email", email);
        json.add("competenciaExperiencia", competenciaExperienciaArray);
        json.addProperty("token", token);

        saida.println(json.toString());
        System.out.println("Dados enviados: " + json.toString());
        saida.flush();

        String resposta = entrada.readLine();
        System.out.println("Resposta do servidor: " + resposta);
    }

    private static void cadastrarVaga(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
     
        String email = UserSession.getInstance(null, null).getEmail();
        String token = UserSession.getInstance(null, null).getToken();

        System.out.println("Digite o titulo da vaga:");
        String nome = teclado.readLine();
        System.out.println("Digite a faixa salarial:");
        String faixaSalarial = teclado.readLine();
        System.out.println("Digite a descricao da vaga:");
        String descricao = teclado.readLine();
        System.out.println("Digite o estado da vaga:");
        String estado = teclado.readLine();

        JsonArray VagaCompetenciaArray = new JsonArray();
        String continuar;

        do {
            JsonObject vagaCompetencia = new JsonObject();
            System.out.println("Digite a competencia:");
            String competencia = teclado.readLine();
            vagaCompetencia.addProperty("competencia", competencia);

            System.out.println("Digite a experiencia:");
            int experiencia = Integer.parseInt(teclado.readLine());
            vagaCompetencia.addProperty("experiencia", experiencia);

            VagaCompetenciaArray.add(vagaCompetencia);

            System.out.println("Deseja exigir outra competencia? (s/n)");
            continuar = teclado.readLine();
        } while ("s".equalsIgnoreCase(continuar));

        JsonObject json = new JsonObject();
        json.addProperty("operacao", "cadastrarVaga");
        json.addProperty("nome", nome);
        json.addProperty("email", email);
        json.addProperty("faixaSalarial", Double.parseDouble(faixaSalarial));
        json.addProperty("descricao", descricao);
        json.addProperty("estado", estado);
        json.add("competencias", VagaCompetenciaArray);
        json.addProperty("token", token);

        saida.println(json.toString());
        System.out.println("Dados enviados: " + json.toString());

        saida.flush();

        String resposta = entrada.readLine();
        System.out.println("Resposta do servidor: " + resposta);

        menuEmpresa(saida, teclado, entrada);
    }

    
    private static void visualizarCandidato(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
      
        String email = UserSession.getInstance(null, null).getEmail();
        String token = UserSession.getInstance(null, null).getToken();

        saida.println(criarMensagemJSON("operacao", "visualizarCandidato", "email", email, "token", token));
        System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "visualizarCandidato",
                "email", email,
                "token", token));
        saida.flush();

        String resposta = entrada.readLine();
        System.out.println("Resposta do servidor: " + resposta);
    }

    private static void visualizarVaga(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
      
        String email = UserSession.getInstance(null, null).getEmail();
        String token = UserSession.getInstance(null, null).getToken();

        System.out.println("Digite o id da vaga:");
        String idVaga = teclado.readLine();


        saida.println(criarMensagemJSON("operacao", "visualizarVaga", "email", email,
                "idVaga", idVaga, "token", token));
        System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "visualizarVaga", "email", email,
                "token", token, "idVaga", idVaga));
        saida.flush();

        String resposta = entrada.readLine();
        System.out.println("Resposta do servidor: " + resposta);

        menuEmpresa(saida, teclado, entrada);
    }

    private static void visualizarCompetenciaExperiencia(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
        
        String email = UserSession.getInstance(null, null).getEmail();
        String token = UserSession.getInstance(null, null).getToken();

        saida.println(criarMensagemJSON("operacao", "visualizarCompetenciaExperiencia",
                "email", email, "token", token));
        System.out.println("Dados enviados: " +
                criarMensagemJSON("operacao", "visualizarCompetenciaExperiencia",
                        "email", email, "token", token));
        saida.flush();

        String resposta = entrada.readLine();
        System.out.println("Resposta do servidor: " + resposta);
    }


    private static void visualizarEmpresa(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
       
        String email = UserSession.getInstance(null, null).getEmail();
        String token = UserSession.getInstance(null, null).getToken();


        saida.println(criarMensagemJSON("operacao", "visualizarEmpresa",
                "email", email, "token", token));
        System.out.println("Dados enviados: " +
                criarMensagemJSON("operacao", "visualizarEmpresa",
                        "email", email, "token", token));
        saida.flush();

        String resposta = entrada.readLine();
        System.out.println("Resposta do servidor: " + resposta);

        menuEmpresa(saida, teclado, entrada);

    }

    private static void loginCandidato(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
        System.out.println("Digite o email do usuário:");
        String email = teclado.readLine();
        System.out.println("Digite a senha:");
        String senha = teclado.readLine();
        saida.println(criarMensagemJSON("operacao", "loginCandidato", "email", email, "senha", senha));
        System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "loginCandidato", "email", email, "senha", senha));


        String resposta = entrada.readLine();
        System.out.println("Resposta do servidor: " + resposta);

        JsonObject respostaJson = JsonParser.parseString(resposta).getAsJsonObject();
        String status = respostaJson.get("status").getAsString();
        if ("200".equals(status)) {
            String token = respostaJson.get("token").getAsString();
            UserSession session = UserSession.getInstance(email, token);
            isLoggedIn = true;
            System.out.println("Login bem sucedido, email:" + email + " token:" + token);
            while (isLoggedIn)
            {

               
                System.out.println("1. Meus Dados(Consultar candidato)");
                System.out.println("2. Cadastrar competencias/experiências");
                System.out.println("3. Consultar minhas competencias/experiências");
                System.out.println("4. Atualizar minhas competencias/experiências");
                System.out.println("5. Deletar minhas competencias/experiências");
                System.out.println("6. Alterar meus dados(Atualizar candidato)");
                System.out.println("7. Deletar meu cadastro(Apagar candidato)");
                System.out.println("8. Pesquisa filtrada");
                System.out.println("9. Logout ");
               
                String opcao = teclado.readLine();
                while ((opcao = teclado.readLine()) != null) {
                    switch (opcao) {
                        case "1":
                            visualizarCandidato(saida, teclado, entrada);
                            break;
                        case "2":
                           
                            cadastrarCompetenciaExperiencia(saida, teclado, entrada);
                            break;
                        case "3":
                            visualizarCompetenciaExperiencia(saida, teclado, entrada);
                            break;
                        
                        case "4":
                            atualizarCompetenciaExperiencia(saida, teclado, entrada);
                            break;
                       
                        case "5":
                            apagarCompetenciaExperiencia(saida, teclado, entrada);
                            
                            break;
                        case "6":
                            atualizarCandidato(saida, teclado, entrada);
                            
                            break;
                        case "7":
                            apagarCandidato(saida, teclado, entrada);
                           
                            break;
                        case "8":
                            listarVagas(saida, teclado, entrada);
                            break;
                        case "9":
                            isLoggedIn = false;
                          
                            logout(saida, entrada);

                            break;

                        default:
                            System.out.println("Opção inválida. Digite novamente.");
                    }

                }
            }
        }else{
                System.out.println("Erro ao fazer login." + respostaJson.get("mensagem").getAsString());
            }


                saida.flush();

                 resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);



            saida.flush();

    }
        private static void loginEmpresa (PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws
        IOException {
            System.out.println("Digite o email da empresa:");
            String email = teclado.readLine();
            System.out.println("Digite a senha:");
            String senha = teclado.readLine();
            saida.println(criarMensagemJSON("operacao", "loginEmpresa", "email", email, "senha", senha));
            System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "loginEmpresa", "email", email, "senha", senha));
            saida.flush();

            String resposta = entrada.readLine();
            System.out.println("Resposta do servidor: " + resposta);


            JsonObject respostaJson = JsonParser.parseString(resposta).getAsJsonObject();
            String status = respostaJson.get("status").getAsString();
            if ("200".equals(status)) {
                String token = respostaJson.get("token").getAsString();
                UserSession session = UserSession.getInstance(email, token);
                isLoggedIn = true;
                System.out.println("Login bem sucedido, email:" + email + " token:" + token);
                while (isLoggedIn)
                {
               
                    System.out.println("1. Meus Dados(Consultar empresa)");
                    System.out.println("2. Cadastrar vagas");
                    System.out.println("3. Consultar minhas vagas");
                    System.out.println("4. Atualizar minhas vagas");
                    System.out.println("5. Deletar minhas vagas");
                    System.out.println("6. Alterar meus dados(Atualizar empresa)");
                    System.out.println("7. Deletar meu cadastro(Apagar empresa)");
                    System.out.println("8. Listar Vagas");
                    System.out.println("9. Pesquisa filtrada");
                    System.out.println("10. Logout");

                    String opcao = teclado.readLine();
                    while ((opcao = teclado.readLine()) != null) {
                        switch (opcao) {
                            case "1":
                                visualizarEmpresa(saida, teclado, entrada);
                                break;
                            case "2":
                              
                                cadastrarVaga(saida, teclado, entrada);
                                break;
                            case "3":
                               
                                visualizarVaga(saida, teclado, entrada);
                                break;
                            case "4":
                               
                                atualizarVaga(saida, teclado, entrada);
                                break;
                            case "5":
                               
                                apagarVaga(saida, teclado, entrada);
                                break;
                            case "6":
                                atualizarEmpresa(saida, teclado, entrada);
                                break;
                            case "7":
                                apagarEmpresa(saida, teclado, entrada);
                                break;
                            case "8":
                                listarVagas(saida, teclado, entrada);
                                break;
                            case "9":
                                filtrarVagas(saida, teclado, entrada);
                                break;
                            case "10":
                                isLoggedIn = false;
                               
                                logout(saida, entrada);
                                break;
                            default:
                                System.out.println("Opção inválida. Digite novamente.");
                        }

                       

                      resposta = entrada.readLine();
                        System.out.println("Resposta do servidor: " + resposta);
                    }

                }
            }
            else{
                    System.out.println("Erro ao fazer login." + respostaJson.get("mensagem").getAsString());
                }



        }


            private static void logout (PrintWriter saida, BufferedReader entrada) throws IOException {

                String token = UserSession.getInstance(null, null).getToken();
                saida.println(criarMensagemJSON("operacao", "logout", "token", token));
                //isLoggedIn = false;
                UserSession.getInstance(null, null).cleanUserSession();
                isLoggedIn = false;
                System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "logout", "token", token));
                saida.flush();

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);

                menu(saida, new BufferedReader(new InputStreamReader(System.in)), entrada);

            }


            private static void atualizarCandidato (PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws
            IOException {

                String token = UserSession.getInstance(null, null).getToken();

                String email = UserSession.getInstance(null, null).getEmail();

                System.out.println("Digite o nome de usuário:");
                String nome = teclado.readLine();
                System.out.println("Digite a senha:");
                String senha = teclado.readLine();
                saida.println(criarMensagemJSON("operacao", "atualizarCandidato", "nome", nome, "email", email, "senha", senha, "token", token));
                System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "atualizarCandidato", "nome", nome, "email", email, "senha", senha, "token", token));
                saida.flush();

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);
            }

            private static void atualizarEmpresa (PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws
            IOException {
                String token = UserSession.getInstance(null, null).getToken();

                String email = UserSession.getInstance(null, null).getEmail();

                System.out.println("Digite a razao social:");
                String razaoSocial = teclado.readLine();
                System.out.println("Digite o cnpj:");
                String cnpj = teclado.readLine();
                System.out.println("Digite a senha:");
                String senha = teclado.readLine();
                System.out.println("Digite a descricao:");
                String descricao = teclado.readLine();
                System.out.println("Digite o ramo:");
                String ramo = teclado.readLine();


                saida.println(criarMensagemJSON("operacao", "atualizarEmpresa",
                        "email", email,
                        "razaoSocial", razaoSocial,
                        "cnpj", cnpj,
                        "senha", senha,
                        "descricao", descricao,
                        "ramo", ramo));
                System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "atualizarEmpresa",
                        "email", email,
                        "razaoSocial", razaoSocial,
                        "cnpj", cnpj,
                        "senha", senha,
                        "descricao", descricao,
                        "ramo", ramo, "token", token));
                saida.flush();

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);

                menuEmpresa(saida, teclado, entrada);
            }

            private static void atualizarCompetenciaExperiencia (PrintWriter saida, BufferedReader
            teclado, BufferedReader entrada) throws IOException {
               
                visualizarCompetenciaExperiencia(saida, teclado, entrada);

                String email = UserSession.getInstance(null, null).getEmail();
                String token = UserSession.getInstance(null, null).getToken();

                JsonArray competenciaExperienciaArray = new JsonArray();
                String continuar;

                do {
                    JsonObject competenciaExperiencia = new JsonObject();
                    System.out.println("Digite a competencia:");
                    String competencia = teclado.readLine();
                    competenciaExperiencia.addProperty("competencia", competencia);

                    System.out.println("Digite a experiencia:");
                    int experiencia = Integer.parseInt(teclado.readLine());
                    competenciaExperiencia.addProperty("experiencia", experiencia);
                    competenciaExperienciaArray.add(competenciaExperiencia);

                    System.out.println("Deseja alterar outra competencia? (s/n)");
                    continuar = teclado.readLine();
                } while ("s".equalsIgnoreCase(continuar));

                JsonObject json = new JsonObject();
                json.addProperty("operacao", "atualizarCompetenciaExperiencia");
                json.addProperty("email", email);
                json.add("competenciaExperiencia", competenciaExperienciaArray);
                json.addProperty("token", token);

                saida.println(json.toString());
                System.out.println("Dados enviados: " + json.toString());
                saida.flush();

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);
            }

            private static void atualizarVaga (PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws
            IOException {
                
                String email = UserSession.getInstance(null, null).getEmail();
                String token = UserSession.getInstance(null, null).getToken();
                System.out.println("Digite o titulo da vaga:");
                String nome = teclado.readLine();
                System.out.println("Digite a faixa salarial:");
                String faixaSalarial = teclado.readLine();
                System.out.println("Digite a descricao da vaga:");
                String descricao = teclado.readLine();
                System.out.println("Digite o estado da vaga:");
                String estado = teclado.readLine();

                JsonArray VagaCompetenciaArray = new JsonArray();
                String continuar;

                do {
                    JsonObject vagaCompetencia = new JsonObject();
                    System.out.println("Digite a competencia:");
                    String competencia = teclado.readLine();
                    vagaCompetencia.addProperty("competencia", competencia);

                    System.out.println("Digite a experiencia:");
                    int experiencia = Integer.parseInt(teclado.readLine());
                    vagaCompetencia.addProperty("experiencia", experiencia);

                    VagaCompetenciaArray.add(vagaCompetencia);

                    System.out.println("Deseja exigir outra competencia? (s/n)");
                    continuar = teclado.readLine();
                } while ("s".equalsIgnoreCase(continuar));

                JsonObject json = new JsonObject();
                json.addProperty("operacao", "atualizarVaga");
                json.addProperty("nome", nome);
                json.addProperty("email", email);
                json.addProperty("faixaSalarial", faixaSalarial);
                json.addProperty("descricao", descricao);
                json.addProperty("estado", estado);
                json.add("competencias", VagaCompetenciaArray);
                json.addProperty("token", token);

                saida.println(json.toString());
                System.out.println("Dados enviados: " + json.toString());

                saida.flush();

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);

                menuEmpresa(saida, teclado, entrada);
            }


            private static void apagarCandidato (PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws
            IOException {
                String token = UserSession.getInstance(null, null).getToken();

                String email = UserSession.getInstance(null, null).getEmail();
               
                saida.println(criarMensagemJSON("operacao", "apagarCandidato", "email", email, "token", token));
                System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "apagarCandidato", "email", email, "token", token));
                saida.flush();

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);


                JsonObject respostaJson = JsonParser.parseString(resposta).getAsJsonObject();
                String status = respostaJson.get("status").getAsString();
                if ("200".equals(status)) {
                    logout(saida, entrada);
                } else {
                    System.out.println("Erro ao apagar candidato." + respostaJson.get("mensagem").getAsString());
                }

            }

            private static void apagarVaga (PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws
            IOException {
             
                String token = UserSession.getInstance(null, null).getToken();
                String email = UserSession.getInstance(null, null).getEmail();

                System.out.println("Digite o id da vaga:");
                String idVaga = teclado.readLine();

                saida.println(criarMensagemJSON("operacao", "apagarVaga", "email", email, "idVaga", idVaga, "token", token));
                System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "apagarVaga", "email", email, "idVaga", idVaga, "token", token));
                saida.flush();

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);

                menuEmpresa(saida, teclado, entrada);
            }

            private static void apagarEmpresa (PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws
            IOException {

                String token = UserSession.getInstance(null, null).getToken();
                String email = UserSession.getInstance(null, null).getEmail();

                saida.println(criarMensagemJSON("operacao", "apagarEmpresa",
                        "email", email, "token", token));
                System.out.println("Dados enviados: " + criarMensagemJSON("operacao", "apagarEmpresa",
                        "email", email, "token", token));
                saida.flush();

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);

                JsonObject respostaJson = JsonParser.parseString(resposta).getAsJsonObject();
                String status = respostaJson.get("status").getAsString();
                if ("200".equals(status)) {
                    logout(saida, entrada);
                } else {
                    System.out.println("Erro ao apagar empresa." + respostaJson.get("mensagem").getAsString());
                }
            }

            private static void apagarCompetenciaExperiencia (PrintWriter saida, BufferedReader teclado, BufferedReader
            entrada) throws IOException {
               
                String email = UserSession.getInstance(null, null).getEmail();
                String token = UserSession.getInstance(null, null).getToken();

                JsonArray competenciaExperienciaArray = new JsonArray();
                String continuar;

                do {
                    JsonObject competenciaExperiencia = new JsonObject();
                    System.out.println("Digite a competencia:");
                    String competencia = teclado.readLine();
                    competenciaExperiencia.addProperty("competencia", competencia);

                    System.out.println("Digite a experiencia:");
                    int experiencia = Integer.parseInt(teclado.readLine());
                    competenciaExperiencia.addProperty("experiencia", experiencia);
                    competenciaExperienciaArray.add(competenciaExperiencia);

                    System.out.println("Deseja apagar outra competencia? (s/n)");
                    continuar = teclado.readLine();
                } while ("s".equalsIgnoreCase(continuar));

                JsonObject json = new JsonObject();
                json.addProperty("operacao", "apagarCompetenciaExperiencia");
                json.addProperty("email", email);
                json.add("competenciaExperiencia", competenciaExperienciaArray);
                json.addProperty("token", token);

                saida.println(json.toString());
                System.out.println("Dados enviados: " + json.toString());
                saida.flush();

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);

            }

            private static void filtrarVagas (PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws
            IOException {
                String token = UserSession.getInstance(null, null).getToken();
                
                System.out.println("Digite o tipo de filtro OU/E:");
                String tipo = teclado.readLine();

                JsonArray competenciasArray = new JsonArray();
                String continuar;

                do {
                    JsonObject competencias = new JsonObject();
                    System.out.println("Digite a competencia:");
                    String competenciasVagas = teclado.readLine();
                    competencias.addProperty("vagas", competenciasVagas);

                    System.out.println("Deseja filtrar por mais outra competencia? (s/n)");
                    continuar = teclado.readLine();
                } while ("s".equalsIgnoreCase(continuar));


                JsonObject json = new JsonObject();
                json.addProperty("operacao", "filtrarVagas");
                json.addProperty("tipo", tipo);
                json.add("competencias", competenciasArray);
                json.addProperty("token", token);

                saida.println(json.toString());
                System.out.println("Dados enviados: " + json.toString());
                saida.flush();

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);

                menuEmpresa(saida, teclado, entrada);
            }

            private static void listarVagas (PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws
            IOException {
                String token = UserSession.getInstance(null, null).getToken();
                String email = UserSession.getInstance(null, null).getEmail();

                saida.println(criarMensagemJSON("operacao", "listarVagas", "email", email, "token", token));
                saida.flush();

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);

                menuEmpresa(saida, teclado, entrada);

            }

        }
