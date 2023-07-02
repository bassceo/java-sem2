package client;

import common.Request;
import common.exceptions.ExitException;
import common.exceptions.RecursiveException;
import common.exceptions.WrongComandException;
import common.exceptions.WrongInputException;
import common.io.ErrorHandler;
import common.io.FileIO;
import common.io.UserIO;
import common.model.*;
import server.commands.Command;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class RequestGenerator {
    private Queue<Request> requests;

    private UserIO userIO;

    private Set<String> commands;
    private ScriptStack scriptStack;

    public RequestGenerator(UserIO userIO){
        this.userIO = userIO;
        requests = new LinkedList<Request>();
        commands = new HashSet<String>();
        init_commands();
        scriptStack = new ScriptStack();
    }

    public RequestGenerator(UserIO userIO,Queue<Request> requests, ScriptStack scriptStack){
        this.userIO = userIO;
        this.requests = requests;
        commands = new HashSet<String>();
        init_commands();
        this.scriptStack = scriptStack;
    }

    public Queue<Request> generateRequests() throws RecursiveException{
        return processCommand(userIO.readLine());
    }

    public Queue<Request> processCommand(String command) throws RecursiveException{
        String[] tokens = command.trim().split("\\s+", 2);
        String commandName = tokens[0];
        String args = tokens.length > 1 ? tokens[1] : "";
        if(commands.contains(commandName)){
            if(commandName.equals("add")){
                if (args.equals("")){
                    throw new RecursiveException("В аргументах должно быть указано имя новой группы!");
                }
                requests.add(new Request(commandName, args, createStudyGroup(args)));
            }else if(commandName.equals("execute_script")){
                if (args.equals("")){
                    throw new RecursiveException("В аргументах должно быть указано название скрипта!");
                }
                executeScript(args);
            }else if(commandName.equals("update")){
                if (args.equals("")){
                    throw new RecursiveException("В аргументах должно быть указано id группы!");
                }
                requests.add(new Request(commandName, args, createStudyGroupForUpdate(args)));
            }else {
                requests.add(new Request(commandName, args));
            }
        }else{
            throw new RecursiveException("Это команда не существует: " + commandName);
        }
        return requests;
    }

    private void executeScript(String args) throws RecursiveException {
        if(args.equals("")){
            throw new RecursiveException(" Не указан скрипт");
        }
        if(scriptStack.contains(args)){
            throw new RecursiveException("В вашем скрипте есть бесконечная рекурсия! Исправьте последовательность скриптов и попробуйте запустить снова!");
        }
        try {
            UserIO fileIO = new FileIO(args);
            scriptStack.push(args);
            RequestGenerator requestGenerator = new RequestGenerator(fileIO,requests,scriptStack);
            String next_line = fileIO.readLine();
            while (next_line != null) {
                if(next_line.trim().isEmpty()){
                    next_line = fileIO.readLine();
                    continue;
                }
                requestGenerator.processCommand(next_line);
                next_line = fileIO.readLine();
            }
            scriptStack.pop();
        }catch (IOException e) {
            throw new RecursiveException("" + e.getMessage());
        }
    }

    private StudyGroup createStudyGroupForUpdate(String args) throws RecursiveException {
        StudyGroup studyGroup = createStudyGroup(args);
        try{
            studyGroup.setName(readInputParameter("Введите название группы",false,String::trim,value -> !value.trim().isEmpty()));
            studyGroup.setId(Long.parseLong(args));
        }catch (Exception e){
            throw new RecursiveException("" + e.getMessage());
        }
        return studyGroup;
    }

    private StudyGroup createStudyGroup(String args)throws RecursiveException{
        try{
            StudyGroup studyGroup = new StudyGroup(1L);
            createGroup(args,studyGroup);
            return studyGroup;
        }catch (Exception e){
            throw new RecursiveException("" + e.getMessage());
        }
    }

    private void createGroup(String args, StudyGroup toAdd) throws RecursiveException{
        Integer studentsCount = readInputParameter("Введите количество студентов", true, Integer::parseInt, value -> value > 0);
        int shouldBeExpelled = readInputParameter("Введите количество студентов на исключение", false, Integer::parseInt, value -> value > 0);
        long averageMark = readInputParameter("Введите среднюю оценку студентов этой группы", false, Long::parseLong, value -> value > 0);
        FormOfEducation formOfEducation = readEnumInputParameter("Выберите форму обучения", false, FormOfEducation.class);
        Coordinates coordinates = new Coordinates();
        try {
            coordinates.setX(readInputParameter("Введите координату X", false, Double::parseDouble, value -> value < 871));
        }catch(Exception e) {
            ErrorHandler.logError(e.getMessage());
        }
        coordinates.setY(readInputParameter("Введите координату Y", false, Double::parseDouble, value -> true ));
        Person groupAdmin = new Person();
        userIO.println("---- Данные о старосте группы ----");
        groupAdmin.setName(readInputParameter("Введите имя админа группы", false, String::trim, value -> value != null));
        groupAdmin.setHeight(readInputParameter("Введите высоту админа", false, Double::parseDouble, value -> value > 0));
        groupAdmin.setNationality(readEnumInputParameter("Выберите страну админа", false, Country.class));
        groupAdmin.setEyeColor(readEnumInputParameter("Выберите цвет глаз админа", false, Color.class));
        try {
            toAdd.setName(args);
            toAdd.setFormOfEducation(formOfEducation);
            toAdd.setCoordinates(coordinates);
            toAdd.setGroupAdmin(groupAdmin);
            toAdd.setAverageMark(averageMark);
            toAdd.setStudentsCount(studentsCount);
            toAdd.setShouldBeExpelled(shouldBeExpelled);
        }catch (WrongInputException e) {
            throw new RecursiveException("" + e.getMessage());
        }
    }
    public void clear(){
        requests.clear();
    }

    private void init_commands(){
        commands.add("help");
        commands.add("exit");
        commands.add("clear");
        commands.add("add");
        commands.add("show");
        commands.add("average_of_students_count");
        commands.add("info");
        commands.add("history");
        commands.add("save");
        commands.add("update");
        commands.add("remove_head");
        commands.add("head");
        commands.add("execute_script");
        commands.add("remove_by_id");
        commands.add("min_by_coordinates");
        commands.add("print_ascending");
    }

    public <T> T readInputParameter(String prompt, boolean isNullable, Function<String, T> parser, Predicate<T> validator) {
        while (true) {
            userIO.print(prompt + ": ");
            String input = userIO.readLine().trim();
            if (!isNullable && input.isEmpty()) {
                userIO.println("Это поле не может быть пустым. Попробуйте еще раз.");
                continue;
            }
            try {
                T value = parser.apply(input);
                if (validator.test(value)) {
                    return value;
                }
                userIO.println("Некорректное значение. Попробуйте еще раз.");
            } catch (IllegalArgumentException e) {
                userIO.println("Некорректный ввод. Попробуйте еще раз.");
            }
        }
    }


    public <T extends Enum<T>> T readEnumInputParameter(String prompt, boolean isNullable, Class<T> enumClass) {
        while (true) {
            userIO.print(prompt + " (" + Arrays.toString(enumClass.getEnumConstants()) + "): ");
            String input = userIO.readLine().trim();
            if (!isNullable && input.isEmpty()) {
                userIO.println("Это поле не может быть пустым. Попробуйте еще раз.");
                continue;
            }
            if(isNullable&&input.isEmpty()){
                return null;
            }
            try {
                return Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                userIO.println("Некорректный ввод. Попробуйте еще раз.");
            }
        }
    }
}
