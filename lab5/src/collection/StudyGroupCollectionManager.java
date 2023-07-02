package com.ifmo.collection;

import com.ifmo.io.ErrorHandler;
import com.ifmo.model.*;
import com.ifmo.exceptions.WrongInputException;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

public class StudyGroupCollectionManager {
    private PriorityQueue<StudyGroup> collection;

    private StudyGroupIO studyGroupIO;

    private String FilneName;

    public String getFileName() {
        return FilneName;
    }

    public StudyGroupCollectionManager(String fileName) {
        collection = new PriorityQueue<>();
        this.studyGroupIO = new StudyGroupCSVTool(collection);
        this.FilneName = fileName;
        return;
    }

    public void addStudyGroup(StudyGroup studyGroup) {
        collection.add(studyGroup);
    }

    public void removeStudyGroup(StudyGroup studyGroup) {
        collection.remove(studyGroup);
    }
    public void removeStudyGroup(Long id) {
        StudyGroup toRemove = collection.peek();
        for (StudyGroup studyGroup : collection) {
            if(studyGroup.getId().equals(id)) {
                toRemove = studyGroup;
                break;
            }
        }
        collection.remove(toRemove);
        return;
    }

    public void removeHead() {
        StudyGroup toRemove = collection.peek();
        collection.remove(toRemove);
        return;
    }

    public StudyGroup head() {
        return collection.peek();
    }

//    public StudyGroup getStudyGroup(int index) {
//        return studyGroups.element(index);
//    }

    public int size() {
        return collection.size();
    }


    public Double countAverageOfStudentsCount(){
        Iterator value = collection.iterator();
        double average = 0D;
        while (value.hasNext()) {
            StudyGroup current = (StudyGroup) value.next();
            average+= (double) current.getStudentsCount();
        }
        average /= (double) collection.size();
        return average;
    }
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public void clear() {
        collection.clear();
    }

    public void loadFromFile(String fileName) throws IOException {
        collection.clear();
        // load from csv file and add to priority queue
        return;
    }
    public void saveToFile(String fileName) throws IOException {
        return;
    }
    public PriorityQueue<StudyGroup> getCollection() {
        return collection;
    }

    public boolean groupExists(Long id) {
        for (StudyGroup current : collection) {
            if (current.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public Long generateId() {
        Long id = 1L;
        for (StudyGroup current : collection) {
            id++;
        }
        return id;
    }

    public void setGroup (Long group, StudyGroup studyGroup) throws WrongInputException {
        for (StudyGroup current : collection) {
            if (current.getId().equals(group)){
                current.setGroup(studyGroup);
                return;
            }
        }
        throw new WrongInputException("Группа с таким id не найдена");
    }



    public StudyGroup getMinCoordinates() {
        return Collections.min(collection, new Comparator<StudyGroup>() {
            @Override
            public int compare(StudyGroup sg1, StudyGroup sg2) {
                if (sg1.getCoordinates().getX() == sg2.getCoordinates().getX()) {
                    return (int) (sg1.getCoordinates().getY() - sg2.getCoordinates().getY());
                } else {
                    return (int) (sg1.getCoordinates().getX() - sg2.getCoordinates().getX());
                }
            }
        });
    }

    public void saveCollectionToFile(String fileName) throws IOException {
        studyGroupIO.saveToFile(fileName);
        return;
    }

    public void loadCollectionFromFile(String fileName){
        studyGroupIO.loadFromFile(fileName);
        return;
    }
}
