import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public void runClient() {
        try {
            client = new Socket("localhost", 5050);
            System.out.println("Conectado ao servidor.");

            output = new ObjectOutputStream(client.getOutputStream());
            output.flush();
            input = new ObjectInputStream(client.getInputStream());

            Scanner scanner = new Scanner(System.in);
            String mensagem = "";

            do {
                System.out.print("CLIENT>>> ");
                mensagem = scanner.nextLine();
                output.writeObject("CLIENT>>> " + mensagem);
                output.flush();

                String resposta = (String) input.readObject();
                System.out.println(resposta);

            } while (!mensagem.equalsIgnoreCase("sair"));

            closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() throws IOException {
        System.out.println("Encerrando conexão...");
        output.close();
        input.close();
        client.close();
    }

    public static void main(String[] args) {
        new Client().runClient();
    }
}
