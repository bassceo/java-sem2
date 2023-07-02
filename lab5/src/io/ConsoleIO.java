package com.ifmo.io;

import java.util.Scanner;

public class ConsoleIO implements UserIO {
    private Scanner scanner;

    public ConsoleIO() {
        scanner = new Scanner(System.in);
        return;
    }

    @Override
    public void print(String data) {
        System.out.print(data);
        return;
    }

    @Override
    public void println(String data) {
        System.out.println(data);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
        return;
    }
}
