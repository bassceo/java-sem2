package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.io.UserIO;
import com.ifmo.model.StudyGroup;

import java.util.PriorityQueue;

public class PrintAscendingCommand extends Command{

    public PrintAscendingCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack,commandMemorizer);
    }

    @Override
    public void execute(String args) {
        PriorityQueue<StudyGroup> current = collectionManager.getCollection();
        if(current.isEmpty()){
            userIO.println("Нет групп");
            return;
        }
        while(!current.isEmpty()){
            userIO.println(current.peek().toString());
            current.poll();
        }
    }
}
