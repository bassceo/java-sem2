package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.io.UserIO;
import com.ifmo.model.StudyGroup;

public class ShowCommand extends Command {
    public ShowCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(String args) {
        if(collectionManager.isEmpty()){
            userIO.println("Список всех групп в коллекции пуст.");
            return;
        }
        userIO.println("Список всех групп в коллекции: ");
        for (StudyGroup studyGroup : collectionManager.getCollection()) {
            userIO.println(studyGroup.toString());
        }
    }
}
