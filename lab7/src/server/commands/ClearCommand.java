package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.UserIO;

public class ClearCommand extends Command {

    public ClearCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack,commandMemorizer);
    }


    @Override
    public void execute(Request request) {
        collectionManager.clear();
    }
}
