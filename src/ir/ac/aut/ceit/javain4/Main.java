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
        int result;
        while (isRun) {
            result = 0;
            System.out.print("> ");

            if (scanner.hasNext("quit")) {
                isRun = false;
                continue;
            }

            String input = scanner.nextLine();
            int sign = 1;
            int num = 0;
            String tmp = "";
            for(int i=0;i<input.length();i++)
            {
                if(input.charAt(i) == '+') {
                    num = Integer.parseInt(tmp);
                    tmp = "";
                    result = result + sign * num;
                    sign = 1;
                }
                else if(input.charAt(i) == '-') {
                    num = Integer.parseInt(tmp);
                    tmp = "";
                    result = result + sign * num;
                    sign = -1;
                }
                else
                    tmp+=input.charAt(i);
            }
            num = Integer.parseInt(tmp);
            result = result + sign * num;
            System.out.println(result);
        }

        System.out.println("Hope you enjoy");
        try {
            srv.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
