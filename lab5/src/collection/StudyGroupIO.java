package com.ifmo.collection;

import com.ifmo.model.StudyGroup;

import java.io.IOException;
import java.util.PriorityQueue;

public interface StudyGroupIO {
    /**
     * 从文件加载
     *
     * @param fileName 文件名称
     */
    public void loadFromFile(String fileName) ;
    public void saveToFile(String fileName) throws IOException;
}
