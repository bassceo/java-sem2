package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.UserIO;
import common.model.StudyGroup;

public class ShowCommand extends Command {
    public ShowCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(Request request) {
        if(collectionManager.isEmpty()){
            userIO.println("[WARNING] Список всех групп в коллекции пуст.");
            return;
        }
        userIO.println("Список всех групп в коллекции: ");
        collectionManager.getCollection().stream()
                .map(StudyGroup::toString)
                .forEach(userIO::println);
    }
}
