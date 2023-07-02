package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.io.UserIO;

public class InfoCommand extends Command {

    public InfoCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(String args) {
        userIO.println("-------- Информация о коллекции --------");
        userIO.println("Тип коллекции: приоритетная очередь.");
        userIO.println("Количество элементов: " + collectionManager.size() + ".");
    }
}
