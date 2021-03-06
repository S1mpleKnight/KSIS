package by.bsuir.ksis.chat.client.ksis.connection;

public interface ConnectionActions {
    void connectionReady(Connection connection);

    void stringReceived(Connection connection, String message);

    void disconnect(Connection connection);

    void exception(Connection connection, Exception e);
}
