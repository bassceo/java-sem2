package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.UserIO;

public class AverageOfStudentCountCommand extends Command{
    public AverageOfStudentCountCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(Request request) {
        if(collectionManager.isEmpty()){
            userIO.println("[WARNING] Список всех групп в коллекции пуст!");
            return;
        }
        userIO.println("Среднее количество студентов во всех группах в коллекции: " + collectionManager.countAverageOfStudentsCount());
    }
}
