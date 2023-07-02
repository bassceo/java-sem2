package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.io.UserIO;

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

    public abstract void execute(String args) throws Exception;

}
