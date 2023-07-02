package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.io.UserIO;

public class ClearCommand extends Command {

    public ClearCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack,commandMemorizer);
    }


    @Override
    public void execute(String args) {
        collectionManager.clear();
    }
}
