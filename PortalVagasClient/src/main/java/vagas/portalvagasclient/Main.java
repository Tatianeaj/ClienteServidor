package vagas.portalvagasclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

import com.google.gson.JsonObject;
import org.json.JSONObject;

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

            System.out.println("Conectado ao servidor. Digite uma opção:");
            System.out.println("1. Consultar candidato");
            System.out.println("2. Cadastrar candidato");
            System.out.println("3. Login");
            System.out.println("4. Atualizar candidato");
            System.out.println("5. Apagar candidato");
            System.out.println("6. Logout");
            System.out.println("7. Sair");

            String opcao;
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
                      isLoggedIn=false;
                          logoutCandidato(saida);
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

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private static void visualizarCandidato(PrintWriter saida, BufferedReader teclado) throws IOException {
       // System.out.println("Digite o nome de usuário:");
       // String usuario = teclado.readLine();
        System.out.println("Digite o email do usuário:");
        String email = teclado.readLine();
       // System.out.println("Digite a senha:");
       // String senha = teclado.readLine();
        saida.println(criarMensagemJSON("operacao", "visualizarCandidato","email", email));
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

    private static void logoutCandidato(PrintWriter saida) throws IOException {
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


    private static String criarMensagemJSON(String... args) {
        JsonObject json = new JsonObject();
        for (int i = 0; i < args.length; i += 2) {
            json.addProperty(args[i], args[i + 1]);
        }
        return json.toString();
    }
}