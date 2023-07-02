package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.io.ErrorHandler;
import com.ifmo.io.UserIO;

public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer comandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, comandMemorizer);
    }

    @Override
    public void execute(String args) {
        if(args==null){
            ErrorHandler.logWarning("Не указан id. Попробуйте еще раз.");
            return;
        }
        try {
            Long id = Long.parseLong(args.trim());
            if(!collectionManager.groupExists(id)){
                ErrorHandler.logWarning("Не найдена группа с данным id: " + id);
                return;
            }
            collectionManager.removeStudyGroup(id);
        } catch (NumberFormatException e) {
            ErrorHandler.logError("Неправильно указан id. Попробуйте еще раз.");
        }
    }
}
