package by.bsuir.ksis.chat.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SimpleConnection {
    private final Socket socket;
    private final Thread thread;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final SimpleConnectionActions action;

    public SimpleConnection(SimpleConnectionActions action, String IP, int port) throws IOException {
        this(new Socket(IP, port), action);
    }

    public SimpleConnection(Socket socket, SimpleConnectionActions action) throws IOException {
        this.action = action;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    action.connectionReady(SimpleConnection.this);
                    while (!thread.isInterrupted()){
                        action.stringReceived(SimpleConnection.this, in.readLine());
                    }
                } catch (IOException e) {
                    action.exception(SimpleConnection.this, e);
                } finally {
                    action.disconnect(SimpleConnection.this);
                }
            }
        });
        thread.start();
    }

    public synchronized void sendString(String message){
        try {
            out.write(message + "\r\n");
            out.flush();
        } catch (IOException e) {
            action.disconnect(SimpleConnection.this);
            disconnect();
        }
    }

    public synchronized void disconnect(){
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            action.exception(SimpleConnection.this, e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleConnection that = (SimpleConnection) o;
        return Objects.equals(socket, that.socket)
                && Objects.equals(thread, that.thread)
                && Objects.equals(in, that.in)
                && Objects.equals(out, that.out)
                && Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, thread, in, out, action);
    }

    @Override
    public String toString() {
        return "Connection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
