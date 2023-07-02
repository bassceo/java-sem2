package com.ifmo.collection;

import com.ifmo.exceptions.WrongInputException;
import com.ifmo.io.ErrorHandler;
import com.ifmo.model.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class StudyGroupCSVTool implements StudyGroupIO {
    private PriorityQueue<StudyGroup> collection;

    public StudyGroupCSVTool(PriorityQueue<StudyGroup> collection) {
        /** Конструктор */
        this.collection = collection;
        return;
    }

    private boolean groupExists(Long id) {
        for (StudyGroup current : collection) {
            if (current.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    /** Сохранение в файл
     *
     * @param fileName - имя файла
     * @throws IOException - исключение ввода-вывода
     */
    @Override
    public void saveToFile(String fileName) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter(fileName), ',', CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

        String[] header = {"id", "name", "coordinates_x", "coordinates_y", "creation_date", "students_count", "should_be_expelled", "average_mark", "form_of_education", "group_admin_name", "group_admin_height", "group_admin_eye_color", "group_admin_nationality"};
        writer.writeNext(header);

        List<String[]> data = new ArrayList<>();
        for (StudyGroup studyGroup : collection) {
            String[] line = {
                    String.valueOf(studyGroup.getId()),
                    studyGroup.getName(),
                    String.valueOf(studyGroup.getCoordinates().getX()),
                    String.valueOf(studyGroup.getCoordinates().getY()),
                    studyGroup.getCreationDate().toString(),
                    String.valueOf(studyGroup.getStudentsCount()),
                    String.valueOf(studyGroup.getShouldBeExpelled()),
                    String.valueOf(studyGroup.getAverageMark()),
                    studyGroup.getFormOfEducation().toString(),
                    studyGroup.getGroupAdmin().getName(),
                    String.valueOf(studyGroup.getGroupAdmin().getHeight()),
                    studyGroup.getGroupAdmin().getEyeColor().toString(),
                    studyGroup.getGroupAdmin().getNationality().toString()
            };
            data.add(line);
        }

        writer.writeAll(data);

        writer.close();
    }



    @Override
    public void loadFromFile(String fileName) {

        try {
            collection.clear();
            CSVReader reader = new CSVReader(new FileReader(fileName));

// Считываем первую строку (заголовки столбцов)
            String[] header = reader.readNext();
            Map<String, Integer> headerMap = new HashMap<>();
            for (int i = 0; i < header.length; i++) {
                headerMap.put(header[i], i);
            }

// Считываем остальные строки
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Создаем объект StudyGroup
                if(groupExists(Long.parseLong(nextLine[headerMap.get("id")]))){
                    ErrorHandler.logError("Данные в файле неверны, существуют две группы с одинаковыми id!");
                    return;
                }

                // Добавляем объект в коллекцию
                StudyGroup studyGroup = new StudyGroup(Long.parseLong(nextLine[headerMap.get("id")]));
                studyGroup.setName(nextLine[headerMap.get("name")]);
                studyGroup.setCoordinates(new Coordinates(Double.parseDouble(nextLine[headerMap.get("coordinates_x")]), Double.parseDouble(nextLine[headerMap.get("coordinates_y")])));
                studyGroup.setCreationDate(LocalDate.parse(nextLine[headerMap.get("creation_date")]));
                studyGroup.setStudentsCount(Integer.parseInt(nextLine[headerMap.get("students_count")]));
                studyGroup.setShouldBeExpelled(Integer.parseInt(nextLine[headerMap.get("should_be_expelled")]));
                studyGroup.setAverageMark(Long.parseLong(nextLine[headerMap.get("average_mark")]));
                studyGroup.setFormOfEducation(FormOfEducation.valueOf(nextLine[headerMap.get("form_of_education")]));
                studyGroup.setGroupAdmin(new Person(nextLine[headerMap.get("group_admin_name")], Double.parseDouble(nextLine[headerMap.get("group_admin_height")]),
                        Color.valueOf(nextLine[headerMap.get("group_admin_eye_color")]), Country.valueOf(nextLine[headerMap.get("group_admin_nationality")])));
                collection.add(studyGroup);
            }

            reader.close();

        } catch (CsvValidationException | IOException e) {
            ErrorHandler.logError("Ошибка чтения файла!" + e.getMessage());
        } catch (NumberFormatException | WrongInputException e) {
            ErrorHandler.logError("Данные в файле неверны!\n" + e.getMessage());
        } catch (Exception e) {
            ErrorHandler.logError("Данные в файле неверны!\n" + e.getMessage());
        }
        return;
    }
}
