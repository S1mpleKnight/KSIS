package by.bsuir.ksis.third.server.service;

import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TransferDataServiceImpl implements TransferDataService {

    @Override
    public void copy(String source, String destination) throws IOException {
        byte[] readFile = read(source);
        addToTheFilesEnd(destination, readFile);
    }

    @Override
    public void addToTheFilesEnd(String pathToFile, byte[] data) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(pathToFile, true))){
            dos.write(data);
            dos.flush();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Can not find the file: " + pathToFile);
        } catch (IOException e) {
            throw new IOException("Can not write to the file: " + pathToFile);
        }
    }

    @Override
    public byte[] read(String pathToFile) throws IOException {
        byte[] result;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(pathToFile))){
            result = dis.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Can not find the file: " + pathToFile);
        } catch (IOException e) {
            throw new IOException("Can not read the file: " + pathToFile);
        }
        return result;
    }

    @Override
    public void update(String pathToFile, byte[] data) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(pathToFile))){
            dos.write(data);
            dos.flush();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Can not find the file: " + pathToFile);
        } catch (IOException e) {
            throw new IOException("Can not write to the file: " + pathToFile);
        }
    }

    @Override
    public void delete(String pathToFile) throws IOException {
        try {
            Files.delete(Path.of(pathToFile));
        } catch (IOException e) {
            throw new IOException("Can not delete file: " + pathToFile);
        }
    }
}