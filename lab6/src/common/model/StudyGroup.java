package common.model;

import common.exceptions.WrongInputException;

import java.io.Serializable;
import java.time.LocalDate;

public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int studentsCount; //Значение поля должно быть больше 0
    private int shouldBeExpelled; //Значение поля должно быть больше 0, Поле может быть null
    private long averageMark; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле может быть null
    private Person groupAdmin; //Поле не может быть null


    /**
     *
     * @param id
     * @throws WrongInputException
     */
    public StudyGroup(Long id) throws WrongInputException {
        if (id <= 0) {
            throw new WrongInputException("Поле не может быть null, Значение поля должно быть больше 0");
        }
        this.id=id;
        this.creationDate = LocalDate.now();
    }

    public void setName(String name) throws WrongInputException {
        if(name==null || name.trim().isEmpty()){
            throw new WrongInputException("Поле не может быть null, Строка не может быть пустой");
        }
        this.name=name;
    }

    public void setStudentsCount(int studentsCount) throws WrongInputException {
        if(studentsCount<=0){
            throw new WrongInputException("Значение поля должно быть больше 0");
        }
        this.studentsCount = studentsCount;
    }

    public void setShouldBeExpelled(int shouldBeExpelled) throws WrongInputException {
        if(shouldBeExpelled<=0){
            throw new WrongInputException("Значение поля должно быть больше 0");
        }
        this.shouldBeExpelled = shouldBeExpelled;
    }


    public void setAverageMark(long averageMark) throws WrongInputException {
        if(averageMark<=0){
            throw new WrongInputException("Значение поля должно быть больше 0");
        }
        this.averageMark = averageMark;
    }

    public void setCoordinates(Coordinates coordinates)throws WrongInputException {
        if(coordinates==null){
            throw new WrongInputException("Поле не может быть null!");
        }
        this.coordinates=coordinates;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation){
        this.formOfEducation = formOfEducation;
    }

    public void setCreationDate(LocalDate creationDate) throws WrongInputException {
        if(creationDate==null){
            throw new WrongInputException("Поле не может быть null!");
        }
        this.creationDate = creationDate;
    }

    public void setGroupAdmin(Person groupAdmin) throws WrongInputException {
        if(groupAdmin==null){
            throw new WrongInputException("Поле не может быть null");
        }
        this.groupAdmin = groupAdmin;
    }

    public void setId(Long id){
        this.id = id;
        return;
    }

    public void setGroup(StudyGroup studyGroup) {
        this.id=studyGroup.getId();
        this.name=studyGroup.getName();
        this.coordinates=studyGroup.getCoordinates();
        this.studentsCount=studyGroup.getStudentsCount();
        this.shouldBeExpelled=studyGroup.getShouldBeExpelled();
        this.averageMark=studyGroup.getAverageMark();
        this.formOfEducation=studyGroup.getFormOfEducation();
        this.groupAdmin=studyGroup.getGroupAdmin();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public long getAverageMark() {
        return averageMark;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public int getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "StudyGroup{" + "id=" + id + ", name='" + name + "'" +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", studentsCount=" + studentsCount +
                ", shouldBeExpelled=" + shouldBeExpelled +
                ", averageMark=" + averageMark +
                ", formOfEducation=" + formOfEducation +
                ", groupAdmin=" + groupAdmin +
                '}';
    }

    @Override
    public int compareTo(StudyGroup studyGroup) {
        return coordinates.compareTo(studyGroup.coordinates);
    }
}