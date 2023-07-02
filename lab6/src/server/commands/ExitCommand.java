package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;

import common.collection.StudyGroupCollectionManager;
import common.exceptions.ExitException;
import common.io.UserIO;

public class ExitCommand extends Command {
    public ExitCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(Request request) throws Exception {
        throw new ExitException();
    }
}
