package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.exceptions.ExitException;
import com.ifmo.io.UserIO;

public class ExitCommand extends Command {
    public ExitCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(String args) throws Exception {
        throw new ExitException();
    }
}
