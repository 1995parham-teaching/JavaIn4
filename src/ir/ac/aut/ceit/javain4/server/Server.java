package ir.ac.aut.ceit.javain4.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server implements Runnable {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private boolean isRun = true;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(1373, 5); // listen on port 1373 with backlog size 5
        this.executorService = Executors.newCachedThreadPool();
    }

    /**
     * stop stops the server and close its socket
     * @throws IOException throws from the closing procedure of server socket
     */
    public void stop() throws IOException {
        this.serverSocket.close();
        isRun = false;
        this.executorService.shutdown();
    }

    public void run() {
        while (isRun) {
            try {
                Socket client = this.serverSocket.accept();
                this.executorService.submit(new Handler(client));
            } catch (IOException e) {
                if (isRun) {
                    logger.severe(e.getMessage());
                }
            }
        }
    }

    private static class Handler implements Runnable {
        private Socket client;
        private boolean isRun = true;

        Handler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(this.client.getOutputStream()));

                while (isRun) {
                    String command = reader.readLine();
                    logger.info(String.format("There is a message from %s: %s", this.client.getInetAddress(), command));
                    if (command.equals("quit")) {
                        this.client.close();
                        isRun = false;
                        continue;
                    }

                    String[] subCommands = command.split(" ");
                    int x = Integer.parseInt(subCommands[1]);
                    int y = Integer.parseInt(subCommands[2]);
                    int result;
                    switch (subCommands[0]) {
                        case "ADD":
                            result = x + y;
                            break;
                        case "SUB":
                            result = x - y;
                            break;
                        default:
                            result = 0;
                    }
                    writer.println(result);
                    writer.flush();
                }
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
        }
    }
}
