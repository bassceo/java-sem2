package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.UserIO;

public abstract class Command {

    protected UserIO userIO;

    protected StudyGroupCollectionManager collectionManager;

    protected ScriptStack scriptStack;

    protected CommandMemorizer commandMemorizer;

    protected Command(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        this.userIO = userIO;
        this.collectionManager = studyGroupCollectionManager;
        this.scriptStack = scriptStack;
        this.commandMemorizer = commandMemorizer;
    }

    public abstract void execute(Request request) throws Exception;

}
