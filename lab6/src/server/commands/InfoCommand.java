package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.UserIO;

public class InfoCommand extends Command {

    public InfoCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(Request request) {
        userIO.println("-------- Информация о коллекции --------");
        userIO.println("Тип коллекции: приоритетная очередь.");
        userIO.println("Количество элементов: " + collectionManager.size() + ".");
    }
}
