package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.UserIO;
import common.model.StudyGroup;

public class MinByCoordinatesCommand extends Command {
    public MinByCoordinatesCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(Request request) {
        if (collectionManager.isEmpty()) {
            userIO.println("[WARNING] Коллекция пуста!");
            return;
        }

        StudyGroup minGroup = collectionManager.getMinCoordinates();
        userIO.println("Объект с минимальными координатами:");
        userIO.println(minGroup.toString());
    }
}
