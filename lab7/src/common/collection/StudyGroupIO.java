package common.collection;

import java.io.IOException;

public interface StudyGroupIO {
    public void loadFromFile(String fileName) ;
    public void saveToFile(String fileName) throws IOException;
}
