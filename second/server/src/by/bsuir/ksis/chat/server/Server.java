package by.bsuir.ksis.chat.server;

import by.bsuir.ksis.connection.Connection;
import by.bsuir.ksis.connection.ConnectionActions;

import java.io.IOException;
import java.net.ServerSocket;

public class Server  implements ConnectionActions {
    public static void main(String[] args) {

    }

    private Server(){
        System.out.println("Server running");
        try (ServerSocket serverSocket = new ServerSocket(8189)){
            while (true){
                try {
                    new Connection();
                } catch (){

                }
            }
        } catch (IOException e) {

        }
    }

    @Override
    public void connectionReady(Connection connection) {

    }

    @Override
    public void stringReceived(Connection connection, String message) {

    }

    @Override
    public void disconnect(Connection connection) {

    }

    @Override
    public void exception(Connection connection, Exception e) {

    }
}
