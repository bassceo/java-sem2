package common.collection;

import common.exceptions.WrongInputException;
import common.model.*;
import server.PostgreDAO;
import server.ServerDAO;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class StudyGroupCollectionManager {
    private PriorityQueue<StudyGroup> collection;

    private StudyGroupIO studyGroupIO;

    private ServerDAO serverDAO;

    private String FilneName;

    private final ReentrantLock lock = new ReentrantLock();
    public String getFileName() {
        return FilneName;
    }

    public StudyGroupCollectionManager(String fileName) {
        collection = new PriorityQueue<>();
        this.studyGroupIO = new StudyGroupCSVTool(collection);
        this.FilneName = fileName;
        this.serverDAO = new PostgreDAO();
        return;
    }

    public void addStudyGroup(StudyGroup studyGroup, String username) {
        lock.lock();
        try {
            if(serverDAO.insertStudyGroup(studyGroup, username)){
                collection.add(studyGroup);
            }else{
                System.out.println("[ERROR] WHEN INSERT");
            }
        } finally {
            lock.unlock();
        }
    }

    public void removeStudyGroup(StudyGroup studyGroup, String username) {
        lock.lock();
        try {
            if(serverDAO.removeStudyGroup(studyGroup, username)){
                collection.remove(studyGroup);
            }else{
                System.out.println("[ERROR] WHEN REMOVE");
            }
        } finally {
            lock.unlock();
        }
    }
    public void removeStudyGroup(Long id, String username) {
        StudyGroup toRemove = collection.peek();
        for (StudyGroup studyGroup : collection) {
            if(studyGroup.getId().equals(id)) {
                toRemove = studyGroup;
                break;
            }
        }
        removeStudyGroup(toRemove, username);
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


    public Double countAverageOfStudentsCount() {
        return collection.stream()
                .mapToInt(StudyGroup::getStudentsCount)
                .average()
                .orElse(0.0);
    }
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public void clear() {
        collection.clear();
    }

    public void loadFromFile(String fileName) throws IOException {
        collection.clear();
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

    public void setGroup (Long group, StudyGroup studyGroup, String username) throws WrongInputException {
        lock.lock();
        try{
            for (StudyGroup current : collection) {
                    if (current.getId().equals(group)){
                        serverDAO.updateStudyGroup(group, studyGroup, username);
                        current.setGroup(studyGroup);
                        return;
                    }
                }
        } finally {
            lock.unlock();
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
//        studyGroupIO.saveToFile(fileName);
        return;
    }

    public void loadCollectionFromFile(String fileName){
//        studyGroupIO.loadFromFile(fileName);
        lock.lock();
        try {
            collection = (PriorityQueue<StudyGroup>) serverDAO.getStudyGroups();
        }finally {
            lock.unlock();
        }

        return;
    }
}
