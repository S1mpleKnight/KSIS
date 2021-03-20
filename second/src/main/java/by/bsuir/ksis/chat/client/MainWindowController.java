package by.bsuir.ksis.chat.client;

import by.bsuir.ksis.chat.connection.SimpleConnection;
import by.bsuir.ksis.chat.connection.SimpleConnectionActions;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainWindowController implements SimpleConnectionActions {
    private SimpleConnection connection;
    private String ip;
    private int port;

    @FXML
    private TextField nicknameField;

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private Button confirmButton;

    @FXML
    private VBox chatBox;

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageArea;

    @FXML
    private Button sendButton;

    @FXML
    void initialize() {
        chatArea.setWrapText(true);

        nicknameField.setText(System.getProperty("user.name"));

        confirmButton.disableProperty().bind(
                Bindings.isEmpty(ipField.textProperty())
                        .or(Bindings.isEmpty(portField.textProperty()))
        );

        confirmButton.setOnMouseClicked(e -> {
            ip = ipField.getText();
            port = Integer.parseInt(portField.getText());
            try {
                connection = new SimpleConnection(this, ip, port);
                App.setConnection(connection);
            } catch (IOException exception) {
                printMessage("Connection exception(client):" + exception);
            }
        });

        sendButton.setOnMouseClicked(e -> {
            String message = messageArea.getText();
            if (!message.equals("")) {
                messageArea.setText(null);
                connection.sendString(nicknameField.getText() + ": " + message);
            }
        });
    }

    @Override
    public void connectionReady(SimpleConnection connection) {
        printMessage("Connection ready");
    }

    @Override
    public void stringReceived(SimpleConnection connection, String message) {
        printMessage(message);
    }

    @Override
    public void disconnect(SimpleConnection connection) {
        printMessage("Connection closed");
    }

    @Override
    public void exception(SimpleConnection connection, Exception e) {
        printMessage("Connection exception(client):" + e);
    }

    private synchronized void printMessage(String message) {
        chatArea.appendText(message + "\n");
    }
}
