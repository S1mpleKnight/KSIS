package by.bsuir.ksis.first;

import java.net.SocketException;
import java.net.UnknownHostException;

import static by.bsuir.ksis.first.MACSearcher.showPCMACs;
import static by.bsuir.ksis.first.MACSearcher.start;

public class Main {

    public static void main(String[] args) {
//        showPCMACs();

        System.out.println();
        try {
            start();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
