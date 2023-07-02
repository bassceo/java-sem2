package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.ErrorHandler;
import common.io.UserIO;

public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer comandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, comandMemorizer);
    }

    @Override
    public void execute(Request request) {
        if(request.getArgs()==null){
            userIO.println("[ERROR] Не указан id. Попробуйте еще раз.");
            return;
        }
        try {
            Long id = Long.parseLong(request.getArgs().trim());
            if(!collectionManager.groupExists(id)){
                userIO.println("[WARNING] Не найдена группа с данным id: " + id);
                return;
            }
            collectionManager.removeStudyGroup(id);
        } catch (NumberFormatException e) {
            userIO.println("[WARNING] Неправильно указан id. Попробуйте еще раз.");
        }
    }
}
