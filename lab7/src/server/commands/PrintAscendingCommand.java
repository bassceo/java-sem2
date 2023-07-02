package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.UserIO;
import common.model.StudyGroup;

import java.util.PriorityQueue;

public class PrintAscendingCommand extends Command{

    public PrintAscendingCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack,commandMemorizer);
    }

    @Override
    public void execute(Request request) {
        PriorityQueue<StudyGroup> current = collectionManager.getCollection();
        if (current.isEmpty()) {
            userIO.println("[WARNING] Нет групп!");
        } else {
            current.stream().map(StudyGroup::toString).forEach(userIO::println);
        }
    }
}
