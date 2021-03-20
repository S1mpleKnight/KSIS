package by.bsuir.ksis.chat.connection;

public interface SimpleConnectionActions {
    void connectionReady(SimpleConnection simpleConnection);

    void stringReceived(SimpleConnection simpleConnection, String message);

    void disconnect(SimpleConnection simpleConnection);

    void exception(SimpleConnection simpleConnection, Exception e);
}
