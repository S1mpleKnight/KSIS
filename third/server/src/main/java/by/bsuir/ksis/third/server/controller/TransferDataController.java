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

import java.io.IOException;

@RestController
public class TransferDataController {
    private final static String START_PATH = ".\\storage\\";
    private final TransferDataService transferDataService;

    @Autowired
    public TransferDataController(TransferDataService transferDataService) {
        this.transferDataService = transferDataService;
    }

    @GetMapping
    public ResponseEntity<?> read(@RequestBody TransferData transferData) {
        String serverPath = START_PATH + transferData.getPathToFile();
        byte[] data = null;
        try {
            data = transferDataService.read(serverPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data != null && data.length != 0
                ? new ResponseEntity<>(data, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addToTheFilesEnd(@RequestBody TransferData transferData) {
        String serverPath = START_PATH + transferData.getPathToFile();
        boolean added = true;
        try {
            transferDataService.addToTheFilesEnd(serverPath, transferData.getData());
        } catch (IOException e) {
            added = false;
            System.out.println(e.getMessage());
        }
        return added
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody TransferData transferData) {
        String serverPath = START_PATH + transferData.getPathToFile();
        boolean updated = true;
        try {
            transferDataService.update(serverPath, transferData.getData());
        } catch (IOException e) {
            updated = false;
            System.out.println(e.getMessage());
        }
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody TransferData transferData) {
        String serverPath = START_PATH + transferData.getPathToFile();
        boolean deleted;
        try {
            transferDataService.delete(serverPath);
            deleted = true;
        } catch (IOException e) {
            deleted = false;
            System.out.println(e.getMessage());
        }
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
