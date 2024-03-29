package ir.ac.aut.ceit.javain4.calculator;

import java.io.*;
import java.net.Socket;

public class RemoteCalculator implements Calculator {
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;

    public RemoteCalculator(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void quit() {
        this.writer.println("quit");
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int add(int a, int b) {
        writer.println(String.format("ADD %d %d", a, b));
        writer.flush();
        int result = 0;
        try {
            String s = reader.readLine();
            result = Integer.parseInt(s);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int sub(int a, int b) {
        writer.println(String.format("SUB %d %d", a, b));
        writer.flush();
        int result = 0;
        try {
            String s = reader.readLine();
            result = Integer.parseInt(s);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }
}
