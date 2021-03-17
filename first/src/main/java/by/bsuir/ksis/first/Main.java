package by.bsuir.ksis.first;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {
        MACSearcher.showPCMACs();

        System.out.println();
        try {
            MACSearcher.start();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
