package by.bsuir.ksis.third.server.controller;

import by.bsuir.ksis.third.server.model.TransferData;
import by.bsuir.ksis.third.server.service.TransferDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class TransferDataController {
    private final static String NOT_MODIFIED = "Can not modify the file";
    private final static String NOT_EXIST_RESPONSE = "The file does not exist";
    private final static String FILE_PATH = "file.txt";
    private final static String START_PATH = ".\\storage\\";
    private final TransferDataService transferDataService;

    @Autowired
    public TransferDataController(TransferDataService transferDataService) {
        this.transferDataService = transferDataService;
    }

    @PutMapping(value = "/move")
    public void move(@RequestBody TransferData transferData){
        if (!Files.exists(Path.of(START_PATH + FILE_PATH))){
            return;
        }
        String receivedPath = transferData.getPathToFile();
        if (!Files.exists(Path.of(new File(transferData.getPathToFile()).getAbsolutePath()))){
            return;
        }
        try {
            transferDataService.copy(START_PATH + FILE_PATH, receivedPath + FILE_PATH);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> read() {
        String serverPath = START_PATH + FILE_PATH;
        byte[] data = null;
        if (Files.exists(Path.of(serverPath))) {
            data = takeData(serverPath, data);
        } else {
            data = NOT_EXIST_RESPONSE.getBytes();
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    private byte[] takeData(String serverPath, byte[] data) {
        try {
            data = transferDataService.read(serverPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @PostMapping
    public ResponseEntity<?> addToTheFilesEnd(@RequestBody TransferData transferData) {
        String serverPath = START_PATH + FILE_PATH;
        boolean added = true;
        try {
            transferDataService.addToTheFilesEnd(serverPath, transferData.getData());
        } catch (IOException e) {
            added = false;
            System.out.println(e.getMessage());
        }
        return added
                ? new ResponseEntity<>("OK", HttpStatus.OK)
                : new ResponseEntity<>(NOT_MODIFIED, HttpStatus.NOT_MODIFIED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody TransferData transferData) {
        String serverPath = START_PATH + FILE_PATH;
        boolean updated = true;
        if (Files.exists(Path.of(serverPath))){
            try {
                transferDataService.update(serverPath, transferData.getData());
            } catch (IOException e) {
                updated = false;
                System.out.println(e.getMessage());
            }
        } else {
            new ResponseEntity<>(NOT_EXIST_RESPONSE, HttpStatus.NOT_FOUND);
        }
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(NOT_MODIFIED, HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping
    public ResponseEntity<?> delete() {
        String serverPath = START_PATH + FILE_PATH;
        boolean deleted = false;
        if (Files.exists(Path.of(serverPath))) {
            try {
                transferDataService.delete(serverPath);
                deleted = true;
            } catch (IOException e) {
                deleted = false;
            }
        } else {
            new ResponseEntity<>(NOT_EXIST_RESPONSE, HttpStatus.NOT_FOUND);
        }
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(NOT_MODIFIED, HttpStatus.NOT_MODIFIED);
    }
}
