package by.bsuir.ksis.third.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferData {
    private String pathToFile;
    private byte[] data;
}
