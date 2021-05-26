package by.bsuir.ksis.third.client.controller;

import by.bsuir.ksis.third.client.model.TransferData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class PrimaryController {
    private static final String URL_SERVER = "http://localhost:8080/";
    private final static String NOT_EXIST_RESPONSE = "The file does not exist";
    private final static String PATH_TO_THE_FILE = "file.txt";

    @FXML
    private Button updateButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button loadButton;
    @FXML
    private TextArea textArea;

    @FXML
    void initialize() {
        deleteButton.setOnAction(e -> {
            deleteFile();
        });

        addButton.setOnAction(e -> {
            addText();
        });

        updateButton.setOnAction(e -> {
            updateText();
        });

        loadButton.setOnAction(e -> {
            takeText();
        });
    }

    private void addText(){
        TransferData transferData = new TransferData(PATH_TO_THE_FILE, textArea.getText().getBytes());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<TransferData> requestBody = new HttpEntity<>(transferData);
        String result = restTemplate.postForObject(URL_SERVER, requestBody, String.class);
        if (result == null) {
            showAlert(false, "The text have not been written");
        } else {
            showAlert(true, "The tile have been written");
        }
    }

    private void updateText(){
        TransferData transferData = new TransferData(PATH_TO_THE_FILE, textArea.getText().getBytes());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<TransferData> requestBody = new HttpEntity<>(transferData);
        restTemplate.put(URL_SERVER, requestBody);
        String result = restTemplate.getForObject(URL_SERVER, String.class);
        if (result == null || !result.equals(textArea.getText())) {
            showAlert(false, "The file have not been updated");
        } else {
            showAlert(true, "The file have been updated");
        }
    }

    private void deleteFile() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(URL_SERVER);
        String result = restTemplate.getForObject(URL_SERVER, String.class);
        if (result == null){
            showAlert(false, "Can not delete the file");
        } else {
            showAlert(true, "The file was deleted");
        }
    }

    private void takeText() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(URL_SERVER, String.class);
        if (result == null || result.equals(NOT_EXIST_RESPONSE)){
            showAlert(false, "Can not load file's content");
        } else {
            textArea.setText(result);
        }
    }

    private static void showAlert(boolean status, String message){
        Alert alert = new Alert(status ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setHeaderText(status ? "Information" : "Error");
        alert.setTitle(status ? "Info" : "Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
