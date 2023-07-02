package com.ifmo;

import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.commands.*;
import com.ifmo.exceptions.ExitException;
import com.ifmo.exceptions.RecursiveException;
import com.ifmo.exceptions.WrongComandException;
import com.ifmo.exceptions.WrongInputException;
import com.ifmo.io.ErrorHandler;
import com.ifmo.io.FileIO;
import com.ifmo.io.UserIO;
import com.ifmo.model.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class CommandProcessor {

    private StudyGroupCollectionManager collectionManager;

    private UserIO userIO;

    private CommandMemorizer comandMemorizer;

    private ScriptStack scriptStack;

    private String filename;

    private Map<String, Command> commands;

    private void initCommands(){
        commands = new HashMap<>();
        commands.put("help", new HelpCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("exit", new ExitCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("clear", new ClearCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("add", new AddCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("show", new ShowCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("average_of_students_count", new AverageOfStudentCountCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("info", new InfoCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("history", new HistoryCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("save", new SaveCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("update", new UpdateCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("remove_head", new RemoveHeadCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("head", new HeadCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("execute_script", new ExecuteScriptCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("remove_by_id", new RemoveByIdCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("min_by_coordinates", new MinByCoordinatesCommand(userIO,collectionManager,scriptStack,comandMemorizer));
        commands.put("print_ascending", new PrintAscendingCommand(userIO,collectionManager,scriptStack,comandMemorizer));
    }

    public CommandProcessor(StudyGroupCollectionManager collectionManager, UserIO userIO, CommandMemorizer comandMemorizer, String filename) {
        this.collectionManager = collectionManager;
        this.userIO = userIO;
        this.comandMemorizer = comandMemorizer;
        this.scriptStack = new ScriptStack();
        this.filename = filename;
        initCommands();
    }

    public CommandProcessor(StudyGroupCollectionManager collectionManager, UserIO userIO, CommandMemorizer comandMemorizer, ScriptStack scriptStack, String filename) {
        this.collectionManager = collectionManager;
        this.userIO = userIO;
        this.comandMemorizer = comandMemorizer;
        this.scriptStack = scriptStack;
        this.filename = filename;
        initCommands();
    }

    public void processCommand(String input, boolean doError) throws Exception {
        String[] tokens = input.trim().split("\\s+", 2);
        String commandName = tokens[0];
        String args = tokens.length > 1 ? tokens[1] : null;

        Command command = commands.get(commandName);
        if (command != null) {
            command.execute(args);
        } else {
            if (doError) {
                throw new WrongComandException(commandName);
            }
            ErrorHandler.logError("Неизвестная команда: " + commandName);
        }

        comandMemorizer.addCommand(commandName);
    }

}