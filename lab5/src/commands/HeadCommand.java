package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.io.ErrorHandler;
import com.ifmo.io.UserIO;

public class HeadCommand extends Command {

    public HeadCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(String args) {
        if(collectionManager.isEmpty()){
            ErrorHandler.logWarning("Нет групп");
            return;
        }
        userIO.println(collectionManager.head().toString());
    }
}
