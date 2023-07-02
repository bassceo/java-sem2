package com.ifmo;

import com.ifmo.exceptions.RecursiveException;
import com.ifmo.exceptions.WrongComandException;
import com.ifmo.io.ConsoleIO;
import com.ifmo.io.ErrorHandler;
import com.ifmo.io.UserIO;
import com.ifmo.exceptions.ExitException;

public class Main {

    public static void main(String[] args) {

        UserIO userIO = new ConsoleIO();

        Application app = new Application(userIO,getFileNameFromEnv());

        app.loadDataFromFile(getFileNameFromEnv());

        while (true) {
            String command = userIO.readLine();
            try {
                app.processCommand(command);
            } catch (ExitException e){
                break;
            } catch ( WrongComandException | RecursiveException e){
                ErrorHandler.logWarning(e.getMessage());
            } catch (Exception e){
                ErrorHandler.logError(e.getMessage());
                break;
            }
        }
        userIO.println(
                "Выход из программы"
        );
        userIO.close();
    }

    private static String getFileNameFromEnv() {
        String fileName = System.getenv("SAVED_COLLECTION");
        if (fileName == null || fileName.isEmpty()) {
            ErrorHandler.logError("Не указано имя файла в переменной окружения SAVED_COLLECTION!");
            System.exit(0);
        }
        return fileName;
    }
}
