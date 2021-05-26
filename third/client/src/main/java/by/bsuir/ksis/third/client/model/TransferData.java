package by.bsuir.ksis.third.client.model;

public class TransferData {
    private String pathToFile;
    private byte[] data;

    public TransferData(){

    }

    public TransferData(String pathToFile, byte[] data) {
        this.pathToFile = pathToFile;
        this.data = data;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
