package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.io.UserIO;

public class HistoryCommand extends Command {
    public HistoryCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(String args){
        userIO.println("Вот последние команды: ");
        for (String command : commandMemorizer.getCommands()) {
            userIO.println(command);
        }
    }
}
