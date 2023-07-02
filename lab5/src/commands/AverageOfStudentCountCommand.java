package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.io.UserIO;

public class AverageOfStudentCountCommand extends Command{
    public AverageOfStudentCountCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(String args) {
        if(collectionManager.isEmpty()){
            userIO.println("Список всех групп в коллекции пуст");
            return;
        }
        userIO.println("Среднее количество студентов во всех группах в коллекции: " + collectionManager.countAverageOfStudentsCount());
    }
}
