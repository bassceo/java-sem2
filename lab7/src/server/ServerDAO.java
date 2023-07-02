package server;

import common.model.StudyGroup;

import javax.sql.RowSet;
import java.util.Collection;

public interface ServerDAO {
    void insertUser(String username, String password);
    boolean findUser (String username, String password);
    boolean insertStudyGroup(StudyGroup group, String username);
    boolean updateStudyGroup(Long id,StudyGroup group, String username);
    boolean removeStudyGroup(StudyGroup group, String username);
    Collection<StudyGroup> getStudyGroups();
}