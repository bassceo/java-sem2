package common.model;

import common.exceptions.WrongInputException;

import java.io.Serializable;

public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Double height; //Поле не может быть null, Значение поля должно быть больше 0
    private Color eyeColor; //Поле может быть null
    private Country nationality; //Поле не может быть null

    public Person(){
        /**
         * Конструктор класса
         */
        return;
    }

    public Person(String name, Double height, Color eyeColor, Country nationality) throws WrongInputException {
        /**
         * Конструктор класса
         *
         * @param name
         * @param height
         * @param eyeColor
         * @param country
         * @throws WrongInputException
         */
        if(name==null){
            throw new WrongInputException("Неверный ввод, поле не может быть null!");
        }
        if(name.equals("")){
            throw new WrongInputException("Неверный ввод, поле не может быть пустым!");
        }
        if(height==null){
            throw new WrongInputException("Неверный ввод, поле не может быть null!");
        }
        if(height<=0){
            throw new WrongInputException("Неверный ввод, поле не может быть меньше или равно нуля!");
        }
        if(nationality==null){
            throw new WrongInputException("Неверный ввод, поле не может быть null!");
        }
        this.name=name;
        this.height=height;
        this.eyeColor=eyeColor;
        this.nationality=nationality;
        return;
    }


    public void setEyeColor(Color eyeColor) {
        /**
         * Задает цвет глаз персоны
         *
         * @param eyeColor цвет глаз  персоны
         */
        this.eyeColor = eyeColor;
        return;
    }

    public void setHeight(Double height) {
        /**
         * Задает высоту персоны
         *
         * @param height
         */
        this.height = height;
        return;
    }

    public void setName(String name) {
        /**
         * Задает имя персоны
         *
         * @param name
         */
        this.name = name;
        return;
    }

    public void setNationality(Country nationality){
        /**
         * Задает страну персоны
         *
         * @param nationality
         */
        this.nationality = nationality;
        return;
    }

    public String getName() {
        /**
         * Возвращает имя персоны
         *
         * @return name
         */
        return name;
    }

    public Color getEyeColor() {
        /**
         * Возвращает цвет глаз персоны
         *
         * @return eyeColor
         */
        return eyeColor;
    }

    public Double getHeight() {
        /**
         * Возвращает высоту персоны
         *
         * @return height
         */
        return height;
    }

    public Country getNationality() {
        /**
         * Возвращает страну персоны
         *
         * @return nationality
         */
        return nationality;
    }

    @Override
    public String toString() {
        /**
         * Возвращает строковое представление персоны
         *
         * @return string representation
         */
        return "Person{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", eyeColor=" + eyeColor +
                ", nationality=" + nationality +
                '}';
    }
}