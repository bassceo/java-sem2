package com.ifmo;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.exceptions.ExitException;
import com.ifmo.exceptions.RecursiveException;
import com.ifmo.exceptions.WrongComandException;
import com.ifmo.io.UserIO;


public class Application {

     // коллекция объектов StudyGroup

    private StudyGroupCollectionManager collectionManager; // менеджер коллекции

    private UserIO userIO;

    private CommandMemorizer commandMemorizer;

    private CommandProcessor mainComandProcessor; // команд парсера

    public Application(UserIO userIO, String fileName) {
        collectionManager = new StudyGroupCollectionManager(fileName);
        commandMemorizer = new CommandMemorizer();
        this.userIO = userIO;
        this.mainComandProcessor = new CommandProcessor(this.collectionManager, this.userIO, this.commandMemorizer, fileName);
    }

    public void processCommand(String command) throws Exception {
        mainComandProcessor.processCommand(command, false);
    }

    public void loadDataFromFile(String fileName) {
        collectionManager.loadCollectionFromFile(fileName);
    }
}