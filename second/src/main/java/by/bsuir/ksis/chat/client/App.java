package by.bsuir.ksis.chat.client;

import by.bsuir.ksis.chat.connection.SimpleConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static SimpleConnection connection;

    public static void main(String[] args) {
        launch();
    }

    public static void setConnection(SimpleConnection connection) {
        App.connection = connection;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void stop() {
        if (connection != null) {
            connection.disconnect();
        }
    }
}
