package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.UserIO;

public class HelpCommand extends Command{

    public HelpCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack,commandMemorizer);
    }
    @Override
    public void execute(Request request) {
        userIO.println("help : вывести справку по доступным командам");
        userIO.println("info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        userIO.println("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        userIO.println("add {element} : добавить новый элемент в коллекцию");
        userIO.println("update id : обновить значение элемента коллекции, id которого равен заданному");
        userIO.println("remove_by_id id : удалить элемент из коллекции по его id");
        userIO.println("clear : очистить коллекцию");
        userIO.println("save : сохранить коллекцию в файл");
        userIO.println("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        userIO.println("exit : завершить программу (без сохранения в файл)");
        userIO.println("head : вывести первый элемент коллекции");
        userIO.println("remove_head : вывести первый элемент коллекции и удалить его");
        userIO.println("history : вывести последние 13 команд (без их аргументов)");
        userIO.println("average_of_students_count : вывести среднее значение поля studentsCount для всех элементов коллекции");
        userIO.println("min_by_coordinates : вывести любой объект из коллекции, значение поля coordinates которого является минимальным");
        userIO.println("print_ascending : вывести элементы коллекции в порядке возрастания");
    }
}
