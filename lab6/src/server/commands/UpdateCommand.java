package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.exceptions.WrongInputException;
import common.io.ErrorHandler;
import common.io.UserIO;
import common.model.*;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public class UpdateCommand extends Command{

    public UpdateCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(Request request) {
        if (request.getArgs().isEmpty()) {
            userIO.println("[WARNING] Необходимо указать id группы");
            return;
        }
        Long id;
        try{
            id = Long.parseLong(request.getArgs());
        } catch (NumberFormatException e){
            userIO.println("[WARNING] Некорректный ввод. Необходимо указать id группы.");
            return;
        }
        if(!collectionManager.groupExists(id)){
            userIO.println("[WARNING] Группа с таким id не существует!");
            return;
        }
        try{
            collectionManager.setGroup(id,request.getStudyGroup());
        } catch (WrongInputException e){
            userIO.println("[WARNING]" + e.getMessage());
            return;
        }
    }
}
