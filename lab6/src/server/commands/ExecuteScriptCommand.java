package server.commands;

import common.Request;
import server.CommandMemorizer;
import server.CommandProcessor;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.exceptions.ExitException;
import common.exceptions.RecursiveException;
import common.exceptions.WrongComandException;
import common.io.ErrorHandler;
import common.io.FileIO;
import common.io.UserIO;

import java.io.IOException;

public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(Request request)  throws Exception {
        // old command
    }
}
