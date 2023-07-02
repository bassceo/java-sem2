package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.ErrorHandler;
import common.io.UserIO;

import java.io.IOException;

public class SaveCommand extends Command{
    public SaveCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(Request request) {
        try {
            collectionManager.saveCollectionToFile(collectionManager.getFileName());
            userIO.println("Коллекция сохранена!");
        }catch (IOException e){
            userIO.println("[WARNING] Не удалось сохранить коллекцию. Ошибка: " + e.getMessage());
        }
    }
}
