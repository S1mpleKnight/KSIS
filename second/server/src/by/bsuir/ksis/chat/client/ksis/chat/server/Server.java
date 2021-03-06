package by.bsuir.ksis.chat.client.ksis.chat.server;

import by.bsuir.ksis.chat.client.ksis.connection.Connection;
import by.bsuir.ksis.chat.client.ksis.connection.ConnectionActions;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server  implements ConnectionActions {
    private final List<Connection> connections = new ArrayList<>();
    private static final Scanner in = new Scanner(System.in);
    private static boolean isStarted = false;

    public static void main(String[] args) {
        while (!isStarted) {
            int port = takePortNumber();
            new Server(port);
        }
    }

    private static int takePortNumber(){
        System.out.print("Enter server's port number: ");
        return in.nextInt();
    }

    private Server(int port){
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true){
                try {
                    System.out.println("Server running");
                    isStarted = true;
                    new Connection(serverSocket.accept(), this);
                } catch (IOException e){
                    System.out.println("Connection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connectionReady(Connection connection) {
        connections.add(connection);
        sendAll("Client connected: " + connection);
    }

    @Override
    public void stringReceived(Connection connection, String message) {
        sendAll(message);
    }

    @Override
    public void disconnect(Connection connection) {
        connections.remove(connection);
        sendAll("Client disconnected: " + connection);
    }

    @Override
    public void exception(Connection connection, Exception e) {
        System.out.println("Connection exception: " + e);
    }

    private void sendAll(String message){
        System.out.println(message);
        for (Connection connection : connections) {
            connection.sendString(message);
        }
    }
}
