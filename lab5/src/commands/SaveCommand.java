package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.io.ErrorHandler;
import com.ifmo.io.UserIO;

import java.io.IOException;

public class SaveCommand extends Command{
    public SaveCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(String args) {
        try {
            collectionManager.saveCollectionToFile(collectionManager.getFileName());
        }catch (IOException e){
            ErrorHandler.logError("Не удалось сохранить коллекцию. Ошибка: " + e.getMessage());
        }
    }
}
