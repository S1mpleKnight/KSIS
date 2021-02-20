package by.bsuir.ksis.first;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MACSearcher {

    public static void showPCMACs() {
        final Enumeration<NetworkInterface> interfaces;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                final byte[] hardwareAddress = interfaces.nextElement().getHardwareAddress();
                if (hardwareAddress != null) {
                    String mac = new BigInteger(1, hardwareAddress).toString(16);
                    mac = String.join("-", String.format("%1$" + 12 + "s", mac)
                            .replaceAll("\\s", "0")
                            .split("(?<=\\G..)"));
                    System.out.println(mac);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void start() throws UnknownHostException, SocketException {
        Enumeration<NetworkInterface> interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
        int ip = takeIPFromLocalHost();

        System.out.println();

        int mask = -1;
        while (interfaceEnumeration.hasMoreElements()) {
            mask = takeMaskValue(interfaceEnumeration, mask);
        }

        showMACAddresses(ip, mask);
    }

    private static int takeIPFromLocalHost() throws UnknownHostException {
        InetAddress localHost = Inet4Address.getLocalHost();
        System.out.println(localHost.getHostAddress());
        System.out.println(localHost.toString());
        return createIPReadingForm(localHost);
    }

    private static int takeMaskValue(Enumeration<NetworkInterface> interfaceEnumeration, int oldValue) throws SocketException {
        NetworkInterface current = interfaceEnumeration.nextElement();
        if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
            return oldValue;
        }
        InterfaceAddress host = current.getInterfaceAddresses().get(0);
        System.out.println(current.getDisplayName());
        return host.getNetworkPrefixLength();
    }

    private static void showMACAddresses(int ip, int mask) {
        int binaryMask = createBinaryMask(mask);
        int address = ip & binaryMask;
        int amount = ~binaryMask - 1;
        showingValidConnections(address, amount);
    }

    private static int createIPReadingForm(InetAddress localHost) {
        byte[] IP_address = localHost.getAddress();
        int ip = IP_address[0] & 255;
        ip <<= 8;
        ip += IP_address[1] & 255;
        ip <<= 8;
        ip += IP_address[2] & 255;
        ip <<= 8;
        ip += IP_address[3] & 255;
        return ip;
    }

    private static int createBinaryMask(int mask) {
        int binaryMask = 0;
        for (int i = 0; i < 31; i++) {
            if (i < mask) {
                binaryMask++;
            }
            binaryMask <<= 1;
        }
        return binaryMask;
    }

    private static void showingValidConnections(int address, int amount) {
        int timeout = 500;
        System.out.println();
        for (int i = 2; i <= amount; i++) {
            long outerAddress = address + i;
            String anotherApi = createIP(outerAddress);
            InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getByName(anotherApi);
                try {
                    System.out.println("IP address: " + inetAddress.getHostAddress());
                    boolean state = inetAddress.isReachable(timeout);
                    System.out.println("Is reachable: " + state);
                    System.out.println(checkARPTable(inetAddress.getHostAddress()));
                } catch (IOException e) {
                    System.out.println("Exception in: " + anotherApi);
                    e.printStackTrace();
                }
            } catch (UnknownHostException e) {
                System.out.println("Exception in: " + anotherApi);
                e.printStackTrace();
            }
        }
    }

    private static String createIP(long outerAddress) {
        short[] buf = new short[4];
        short check = 255;
        buf[3] = (short) (outerAddress & check);
        buf[2] = (short) ((outerAddress >> 8) & check);
        buf[1] = (short) ((outerAddress >> 16) & check);
        buf[0] = (short) ((outerAddress >> 24) & check);
        return buf[0] + "." + buf[1] + "." + buf[2] + "." + buf[3];
    }

    private static String checkARPTable(String ip) throws IOException {
        String systemInput = takeARP(ip);
        String mac = "";
        Pattern pattern = Pattern.compile("\\s*([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})");
        Matcher matcher = pattern.matcher(systemInput);
        if (matcher.find()) {
            mac = mac + matcher.group().replaceAll("\\s", "");
        } else {
            System.out.println("No string found");
        }
        return mac;
    }

    private static String takeARP(String ip) throws IOException {
        String systemInput;
        Runtime.getRuntime().exec("arp -a");
        Scanner s = new Scanner(Runtime.getRuntime().exec("arp -a " + ip).getInputStream()).useDelimiter("\\A");
        systemInput = s.next();
        return systemInput;
    }
}
