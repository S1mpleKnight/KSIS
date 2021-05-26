package by.bsuir.ksis.third.client.starter;

import by.bsuir.ksis.third.client.model.TransferData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static final String SCENES_PATH = "primary.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(SCENES_PATH)));
            Group group = new Group();
            group.getChildren().add(node);
            primaryStage.setScene(new Scene(group));
            primaryStage.setResizable(false);
            primaryStage.setTitle("Lab_3");
            primaryStage.show();
        } catch (IOException e) {
            showAlert();
        }
    }

    private static void showAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Fatal error");
        alert.setTitle("Error");
        alert.setContentText("Can not open application");
        alert.showAndWait();
    }
}
