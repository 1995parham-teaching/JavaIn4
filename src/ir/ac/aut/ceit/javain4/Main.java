package ir.ac.aut.ceit.javain4;

import ir.ac.aut.ceit.javain4.calculator.Calculator;
import ir.ac.aut.ceit.javain4.calculator.RemoteCalculator;
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

        Calculator calculator;
        try {
            calculator = new RemoteCalculator("127.0.0.1", 1373);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

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
            int sign = 1; // stores the last sign
            StringBuilder tmp = new StringBuilder(); // builds the string that is used as the last read number
            int num;
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '+') {
                    num = Integer.parseInt(tmp.toString());
                    tmp = new StringBuilder();
                    result = calculator.add(result, sign * num);
                    sign = 1;
                } else if(input.charAt(i) == '-') {
                    num = Integer.parseInt(tmp.toString());
                    tmp = new StringBuilder();
                    result = calculator.add(result, sign * num);
                    sign = -1;
                } else {
                    tmp.append(input.charAt(i));
                }
            }
            num = Integer.parseInt(tmp.toString());
            result = calculator.add(result, sign * num);

            System.out.println(result);
        }

        System.out.println("Hope you enjoy");
        try {
            srv.stop();
            calculator.quit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
