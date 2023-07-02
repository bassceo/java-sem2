package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.io.UserIO;
import com.ifmo.model.StudyGroup;

public class MinByCoordinatesCommand extends Command {
    public MinByCoordinatesCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(String args){
        if (collectionManager.isEmpty()) {
            userIO.println("Коллекция пуста");
            return;
        }

        StudyGroup minGroup = collectionManager.getMinCoordinates();
        userIO.println("Объект с минимальными координатами:");
        userIO.println(minGroup.toString());
    }
}
