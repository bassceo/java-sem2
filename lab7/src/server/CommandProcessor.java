package server;

import client.ScriptStack;
import common.Request;
import common.collection.StudyGroupCollectionManager;
import server.commands.*;
import common.exceptions.WrongComandException;
import common.io.ErrorHandler;
import common.io.UserIO;

import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {

    private StudyGroupCollectionManager collectionManager;

    private UserIO userIO;

    private CommandMemorizer comandMemorizer;

    private ScriptStack scriptStack;

    private String filename;

    private Map<String, Command> commands;

    private ServerDAO usersDAO;

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
        usersDAO = new PostgreDAO();
        initCommands();
    }

    public CommandProcessor(StudyGroupCollectionManager collectionManager, UserIO userIO, CommandMemorizer comandMemorizer, ScriptStack scriptStack, String filename) {
        this.collectionManager = collectionManager;
        this.userIO = userIO;
        this.comandMemorizer = comandMemorizer;
        this.scriptStack = scriptStack;
        this.filename = filename;
        usersDAO = new PostgreDAO();
        initCommands();
    }

    public void processCommand(Request request, boolean doError) throws Exception {

        if(request.getCommand().equals("register")){
            usersDAO.insertUser(request.getLogin(), request.getPassword());
            if(!usersDAO.findUser(request.getLogin(), request.getPassword())){
                userIO.println("Не удалось зарегистрироваться, возможно ваш логин не уникален!");
                return;
            }

            userIO.println("Вы успешно зарегистрировались!");

            return;
        }

        if(request.getCommand().equals("login") ){
            if(!usersDAO.findUser(request.getLogin(), request.getPassword())){
                userIO.println("Не удалось авторизоваться, неправильный логин или пароль!");
                return;
            }

            userIO.println("Вы успешно авторизовались!");

            return;
        }

        if(!usersDAO.findUser(request.getLogin(), request.getPassword())){
            userIO.println("Невозможно выполнить команду, вы не авторизованны!");
            return;
        }

        Command command = commands.get(request.getCommand());



        if (command != null) {
            command.execute(request);
        } else {
            if (doError) {
                throw new WrongComandException(request.getCommand());
            }
            ErrorHandler.logError("Неизвестная команда: " + request.getCommand());
        }

        comandMemorizer.addCommand(request.getCommand());
    }

}