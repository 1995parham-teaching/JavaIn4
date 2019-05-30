package ir.ac.aut.ceit.javain4;

import ir.ac.aut.ceit.javain4.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Server srv;
        try {
            srv = new Server();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        new Thread(srv).start();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Distributed Calculator");
        boolean isRun = true;
        while (isRun) {
            System.out.println("1) Add");
            System.out.println("2) Sub");
            System.out.println("3) Quit");
            System.out.print("> ");

            while (!scanner.hasNextInt()) {
                scanner.next();
            }

            int select = scanner.nextInt();
            switch (select) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    isRun = false;
                    break;
            }
        }

        System.out.println("Hope you enjoy");
        try {
            srv.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
