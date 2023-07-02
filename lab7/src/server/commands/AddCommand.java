package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.UserIO;
import common.exceptions.WrongInputException;
import common.io.ErrorHandler;
import common.model.*;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public class AddCommand extends Command {

    public AddCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(Request request) {
        StudyGroup toAdd = request.getStudyGroup();
        toAdd.setId(collectionManager.generateId());
        try {
            collectionManager.addStudyGroup(toAdd,request.getLogin());
        } catch (IllegalArgumentException e) {
            userIO.println("[SERVER:ERROR] " + e.getMessage());
        }
    }
}
