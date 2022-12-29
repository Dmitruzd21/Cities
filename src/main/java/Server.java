import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8989)) { // стартуем сервер один(!) раз
            System.out.println("Сервер запущен");
            List<Socket> clients = new ArrayList<>();
            String lastCity = "";
            while (true) { // в цикле(!) принимаем подключения
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    clients.add(clientSocket);
                    System.out.println("Подключился новый клиент с портом: " + clientSocket.getPort());
                    System.out.println("К серверу подключилось: " + clients.size() + " клиент/ов.");
                    if (clients.size() == 1) {
                        out.println("???");
                        lastCity = in.readLine();
                        out.println("OK");
                    } else {
                        out.println("Последний город: " + lastCity + ", теперь ваша очередь.");
                        String newCity = in.readLine();
                        if (newCity.toLowerCase().charAt(0) == lastCity.toLowerCase().charAt(lastCity.length() - 1)) {
                            lastCity = newCity;
                            out.println("OK");
                        } else {
                            out.println("NOT OK");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
