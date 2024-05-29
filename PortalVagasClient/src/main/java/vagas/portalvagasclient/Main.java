package vagas.portalvagasclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

import com.google.gson.JsonObject;

public class Main {
    private static boolean isLoggedIn = false;
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

            if (userType.equals("1")) {
            System.out.println("Conectado ao servidor. Digite uma opção:");
            System.out.println("1. Consultar candidato");
            System.out.println("2. Cadastrar candidato");
            System.out.println("3. Login");
            System.out.println("4. Atualizar candidato");
            System.out.println("5. Apagar candidato");
            System.out.println("6. Logout");
            System.out.println("7. Sair");
            } else {
                System.out.println("Conectado ao servidor. Digite uma opção:");
                System.out.println("1. Consultar empresa");
                System.out.println("2. Cadastrar empresa");
                System.out.println("3. Login");
                System.out.println("4. Atualizar empresa");
                System.out.println("5. Apagar empresa");
                System.out.println("6. Logout");
                System.out.println("7. Sair");
                // Adicione aqui as opções do menu para empresas
            }



            String opcao;
            if (userType.equals("1")) {
                while ((opcao = teclado.readLine()) != null) {
                    switch (opcao) {
                        case "1":
                            visualizarCandidato(saida, teclado);
                            break;
                        case "2":
                            cadastrarCandidato(saida, teclado);
                            break;
                        case "3":
                            loginCandidato(saida, teclado, entrada);
                            break;
                        case "4":
                            atualizarCandidato(saida, teclado);
                            break;
                        case "5":
                            apagarCandidato(saida, teclado);
                            break;
                        case "6":
                            isLoggedIn = false;
                            logout(saida);
                            break;

                        case "7":
                            System.out.println("Saindo...");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Opção inválida. Digite novamente.");
                    }

                    // saida.flush();

                    String resposta = entrada.readLine();
                    System.out.println("Resposta do servidor: " + resposta);
                }
            }
            else {//aqui
                    while ((opcao = teclado.readLine()) != null) {
                        switch (opcao) {
                            case "1":
                                visualizarEmpresa(saida, teclado);
                                break;
                            case "2":
                                cadastrarEmpresa(saida, teclado);
                                break;
                            case "3":
                                loginEmpresa(saida, teclado, entrada);
                                break;
                            case "4":
                                atualizarEmpresa(saida, teclado);
                                break;
                            case "5":
                                apagarEmpresa(saida, teclado);
                                break;
                            case "6":
                                isLoggedIn = false;
                                logout(saida);
                                break;

                            case "7":
                                System.out.println("Saindo...");
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Opção inválida. Digite novamente.");
                        }

                        // saida.flush();

                        String resposta = entrada.readLine();
                        System.out.println("Resposta do servidor: " + resposta);
                    }
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String criarMensagemJSON(String... args) {
        JsonObject json = new JsonObject();
        for (int i = 0; i < args.length; i += 2) {
            json.addProperty(args[i], args[i + 1]);
        }
        return json.toString();
    }


    private static void cadastrarCandidato(PrintWriter saida, BufferedReader teclado) throws IOException {
        System.out.println("Digite o nome de usuário:");
        String nome = teclado.readLine();
        System.out.println("Digite o email do usuário:");
        String email = teclado.readLine();
        System.out.println("Digite a senha:");
        String senha = teclado.readLine();
        saida.println(criarMensagemJSON("operacao", "cadastrarCandidato", "nome", nome, "email", email, "senha", senha));
        saida.flush();
    }



    private static void cadastrarEmpresa(PrintWriter saida, BufferedReader teclado) throws IOException {
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
        saida.flush();
    }


    private static void visualizarCandidato(PrintWriter saida, BufferedReader teclado) throws IOException {
        System.out.println("Digite o email do usuário:");
        String email = teclado.readLine();

        saida.println(criarMensagemJSON("operacao", "visualizarCandidato","email", email));
        saida.flush();


    }
    private static void visualizarEmpresa(PrintWriter saida, BufferedReader teclado) throws IOException {
        System.out.println("Digite o email da empresa:");
        String email = teclado.readLine();


        saida.println(criarMensagemJSON("operacao", "visualizarEmpresa", "email", email));
        saida.flush();

    }

    private static void loginCandidato(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
        System.out.println("Digite o email do usuário:");
        String email = teclado.readLine();
        System.out.println("Digite a senha:");
        String senha = teclado.readLine();
        saida.println(criarMensagemJSON("operacao", "loginCandidato", "email", email, "senha", senha));
        saida.flush();

        // Verificar se o login foi bem-sucedido
        //String resposta = entrada.readLine();
        //JSONObject json = new JSONObject(mensagem);

        // Extrai os valores
        //String operacao = json.getString("status");

      //if (respostaJson.getInt("status")==200) {
         isLoggedIn = true;
        //} else {
          //  isLoggedIn = false;
        //}

    }

    private static void loginEmpresa(PrintWriter saida, BufferedReader teclado, BufferedReader entrada) throws IOException {
        System.out.println("Digite o email da empresa:");
        String email = teclado.readLine();
        System.out.println("Digite a senha:");
        String senha = teclado.readLine();
        saida.println(criarMensagemJSON("operacao", "loginEmpresa", "email", email, "senha", senha));
        saida.flush();

        isLoggedIn = true;

    }



    private static void logout(PrintWriter saida) throws IOException {
        saida.println(criarMensagemJSON("operacao", "logout", "token", UUID.randomUUID().toString()));
        //isLoggedIn = false;
        saida.flush();

    }

    private static void atualizarCandidato(PrintWriter saida, BufferedReader teclado) throws IOException {
        System.out.println("Digite o nome de usuário:");
        String nome = teclado.readLine();
        System.out.println("Digite o email do usuário:");
        String email = teclado.readLine();
        System.out.println("Digite a senha:");
        String senha = teclado.readLine();
        saida.println(criarMensagemJSON("operacao", "atualizarCandidato", "nome", nome, "email", email, "senha", senha));
        saida.flush();
    }


    private static void atualizarEmpresa(PrintWriter saida, BufferedReader teclado) throws IOException {
        System.out.println("Digite o email:");
        String email = teclado.readLine();
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
        saida.flush();
    }


    private static void apagarCandidato(PrintWriter saida, BufferedReader teclado) throws IOException {
        //System.out.println("Digite o nome de nome:");
        //String usuario = teclado.readLine();
        System.out.println("Digite o email do usuário:");
        String email = teclado.readLine();
        //System.out.println("Digite a senha:");
        //String senha = teclado.readLine();
        saida.println(criarMensagemJSON("operacao", "apagarCandidato","email", email));
        saida.flush();
    }

    private static void apagarEmpresa(PrintWriter saida, BufferedReader teclado) throws IOException {

        System.out.println("Digite o email:");
        String email = teclado.readLine();

        saida.println(criarMensagemJSON("operacao", "apagarEmpresa",
                "email", email));
        saida.flush();
    }



}