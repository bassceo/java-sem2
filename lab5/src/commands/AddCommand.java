package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.exceptions.WrongInputException;
import com.ifmo.io.ErrorHandler;
import com.ifmo.io.UserIO;
import com.ifmo.model.*;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public class AddCommand extends Command {

    public AddCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
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

    private void createGroup(String args, StudyGroup toAdd) {
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
            ErrorHandler.logError(e.getMessage());
        }
    }

    @Override
    public void execute(String args) {
        if (args == null) {
            userIO.println("Необходимо указать имя группы");
            return;
        }
        StudyGroup toAdd;
        try {
            toAdd = new StudyGroup(collectionManager.generateId());
        }catch(WrongInputException e) {
            ErrorHandler.logError("Не удалось создать группу. Ошибка: " + e.getMessage());
            return;
        }
        createGroup(args, toAdd);
        try {
            collectionManager.addStudyGroup(toAdd);
        } catch (IllegalArgumentException e) {
            ErrorHandler.logError(e.getMessage());
        }
    }
}
