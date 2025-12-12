import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket server;
    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public void runServer() {
        try {
            server = new ServerSocket(5050, 100);
            System.out.println("Servidor iniciado na porta 5050...");

            while (true) {
                System.out.println("\nAguardando conexão...");
                connection = server.accept();
                System.out.println("Conectado a: " + connection.getInetAddress().getHostName());

                output = new ObjectOutputStream(connection.getOutputStream());
                output.flush();
                input = new ObjectInputStream(connection.getInputStream());

                String mensagem = "";

                do {
                    try {
                        mensagem = (String) input.readObject();
                        System.out.println(mensagem);

                        if (mensagem.equals("CLIENT>>> olá")) {
                            sendMessage("SERVER>>> olá, tudo bem?");
                        } else if (mensagem.contains("tudo certo!")) {
                            sendMessage("SERVER>>> ok");
                        } else if (mensagem.toLowerCase().contains("sair")) {
                            sendMessage("SERVER>>> sair");
                        }

                    } catch (ClassNotFoundException e) {
                        System.out.println("Tipo de objeto desconhecido!");
                    }
                } while (!mensagem.equalsIgnoreCase("CLIENT>>> sair"));

                closeConnection();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) throws IOException {
        output.writeObject(message);
        output.flush();
        System.out.println(message);
    }

    private void closeConnection() throws IOException {
        System.out.println("Encerrando conexão...");
        output.close();
        input.close();
        connection.close();
    }

    public static void main(String[] args) {
        new Server().runServer();
    }
}
