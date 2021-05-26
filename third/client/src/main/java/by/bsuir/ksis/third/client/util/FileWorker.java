package by.bsuir.ksis.third.client.util;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWorker {
    public static void writeTheFile(String pathToFile, byte[] data) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(pathToFile))){
            dos.write(data);
            dos.flush();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Can not find the file: " + pathToFile);
        } catch (IOException e) {
            throw new IOException("Can not write to the file: " + pathToFile);
        }
    }
}
