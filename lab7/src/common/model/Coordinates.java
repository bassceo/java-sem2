package common.model;

import common.exceptions.WrongInputException;

import java.io.Serializable;

public class Coordinates implements Comparable<Coordinates>, Serializable {
    private Double x; //Максимальное значение поля: 871, Поле не может быть null
    private double y;
    private boolean isYNull;

    public Coordinates(Double x, double y) throws WrongInputException {
        this.x = x;
        if(x > 871) {
            throw new WrongInputException("Неверный ввод координат, координата x не может быть больше 871!");
        }else if(x.isNaN()) {
            throw new WrongInputException("Неверный ввод координат, координата x не может null!");
        }
        this.y = y;
        this.isYNull = false;
        return;
    }
    public Coordinates() {
        this.isYNull = true;
        return;
    }
    public void setX(Double x) throws WrongInputException {
        if(x > 871) {
            throw new WrongInputException("Неверный ввод координат, координата x не может быть больше 871!");
        }else if(x.isNaN()) {
            throw new WrongInputException("Неверный ввод координат, координата x не может null!");
        }
        this.x = x;
        return;
    }
    public void setY(double y) {
        this.y = y;
        this.isYNull=false;
        return;
    }
    public Double getX() {
        return x;
    }
    public Double getY() {
        if(isYNull){
            return null;
        }
        return y;
    }

    @Override
    public String toString() {
        if(isYNull) {
            return "Coordinates{" +
                    "x=" + x +
                    ", y=null" +
                    '}';
        }
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Coordinates other) {
        if (this.x.equals(other.x)) {
            return Double.compare(this.y, other.y);
        } else {
            return Double.compare(this.x, other.x);
        }
    }
}
