package server;

import common.Request;
import common.collection.StudyGroupCollectionManager;
import common.io.UserIO;


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

    public void processCommand(Request request) throws Exception {
        mainComandProcessor.processCommand(request, false);
    }

    public void loadDataFromFile(String fileName) {
        collectionManager.loadCollectionFromFile(fileName);
    }
}