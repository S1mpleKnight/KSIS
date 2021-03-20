package by.bsuir.ksis.chat.server;


import by.bsuir.ksis.chat.connection.SimpleConnection;
import by.bsuir.ksis.chat.connection.SimpleConnectionActions;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server implements SimpleConnectionActions {
    private static final Scanner in = new Scanner(System.in);
    private static boolean isStarted = false;
    private final ArrayList<SimpleConnection> SimpleConnections = new ArrayList<>();

    private Server(int port) {
        System.out.println("Server running");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try {
                    isStarted = true;
                    new SimpleConnection(serverSocket.accept(), this);
                } catch (IOException e) {
                    System.out.println("SimpleConnection exception(server): " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        while (!isStarted) {
            int port = takePortNumber();
            new Server(port);
        }
    }

    private static int takePortNumber() {
        System.out.print("Enter server's port number: ");
        return in.nextInt();
    }

    @Override
    public void connectionReady(SimpleConnection SimpleConnection) {
        SimpleConnections.add(SimpleConnection);
        sendAll("Client connected: " + SimpleConnection);
    }

    @Override
    public void stringReceived(SimpleConnection SimpleConnection, String message) {
        sendAll(message);
    }

    @Override
    public void disconnect(SimpleConnection SimpleConnection) {
        SimpleConnections.remove(SimpleConnection);
        SimpleConnections.trimToSize();
        sendAll("Client disconnected: " + SimpleConnection);
    }

    @Override
    public void exception(SimpleConnection SimpleConnection, Exception e) {
        System.out.println("SimpleConnection exception(server): " + e);
    }

    private void sendAll(String message) {
        System.out.println(message);
        SimpleConnections.trimToSize();
        for (int i = 0; i < SimpleConnections.size(); i++) {
            SimpleConnections.get(i).sendString(message);
        }
    }
}
