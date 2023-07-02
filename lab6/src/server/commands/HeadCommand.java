package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.ErrorHandler;
import common.io.UserIO;

public class HeadCommand extends Command {

    public HeadCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(Request request) {
        if(collectionManager.isEmpty()){
            userIO.println("[WARNING] Нет групп!");
            return;
        }
        userIO.println(collectionManager.head().toString());
    }
}
