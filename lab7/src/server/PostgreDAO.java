package server;

import common.model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class PostgreDAO implements ServerDAO {
    private Connection connection;
    private PreparedStatement insertUserStatement;
    private PreparedStatement findUserStatement;
    private PreparedStatement insertStudyGroupStatement;
    private PreparedStatement updateStudyGroupStatement;
    private PreparedStatement removeStudyGroupStatement;
    private PreparedStatement getStudyGroupsStatement;

    public PostgreDAO() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("db.properties"));
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            connection = DriverManager.getConnection(url, username, password);
            prepareStatements();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareStatements() throws SQLException {
        insertUserStatement = connection.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?)");
        findUserStatement = connection.prepareStatement("SELECT * FROM users WHERE name = ? AND password = ?");
        insertStudyGroupStatement = connection.prepareStatement("INSERT INTO groups (name, coordinates_x, coordinates_y, creation_date, students_count, " +
                "should_be_expelled, average_mark, form_of_education, group_admin_name, group_admin_height, group_admin_eye_color, group_admin_nationality, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        updateStudyGroupStatement = connection.prepareStatement("UPDATE groups SET name = ?, coordinates_x = ?, coordinates_y = ?, creation_date = ?, " +
                "students_count = ?, should_be_expelled = ?, average_mark = ?, form_of_education = ?, group_admin_name = ?, group_admin_height = ?, " +
                "group_admin_eye_color = ?, group_admin_nationality = ? WHERE id = ? AND user_id = ?");
        removeStudyGroupStatement = connection.prepareStatement("DELETE FROM groups WHERE id = ? AND user_id = ?");
        getStudyGroupsStatement = connection.prepareStatement("SELECT * FROM groups");
    }

    @Override
    public void insertUser(String username, String password) {
        try {
            insertUserStatement.setString(1, username);
            insertUserStatement.setString(2, hashAndSaltPassword(password));
            insertUserStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean findUser(String username, String password) {
        try {
            findUserStatement.setString(1, username);
            findUserStatement.setString(2, hashAndSaltPassword(password));
            ResultSet resultSet = findUserStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertStudyGroup(StudyGroup group, String username) {
        try {
            return setStatement(group, username, insertStudyGroupStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean setStatement(StudyGroup group, String username, PreparedStatement insertStudyGroupStatement) throws SQLException {
        if (insertStudyGroupStatement == this.insertStudyGroupStatement){
            insertStudyGroupStatement.setString(1, group.getName());
            insertStudyGroupStatement.setDouble(2, group.getCoordinates().getX());
            insertStudyGroupStatement.setDouble(3, group.getCoordinates().getY());
            insertStudyGroupStatement.setDate(4, Date.valueOf(group.getCreationDate()));
            insertStudyGroupStatement.setInt(5, group.getStudentsCount());
            insertStudyGroupStatement.setInt(6, group.getShouldBeExpelled());
            insertStudyGroupStatement.setDouble(7, group.getAverageMark());
            insertStudyGroupStatement.setString(8, group.getFormOfEducation().toString());
            insertStudyGroupStatement.setString(9, group.getGroupAdmin().getName());
            insertStudyGroupStatement.setDouble(10, group.getGroupAdmin().getHeight());
            insertStudyGroupStatement.setString(11, getString(group.getGroupAdmin().getEyeColor()));
            insertStudyGroupStatement.setString(12, getString(group.getGroupAdmin().getNationality()));
//            insertStudyGroupStatement.setLong(12, group.getId());
            insertStudyGroupStatement.setInt(13, getUserIdByUsername(username));
        }else{
            insertStudyGroupStatement.setString(1, group.getName());
            insertStudyGroupStatement.setDouble(2, group.getCoordinates().getX());
            insertStudyGroupStatement.setDouble(3, group.getCoordinates().getY());
            insertStudyGroupStatement.setDate(4, Date.valueOf(group.getCreationDate()));
            insertStudyGroupStatement.setInt(5, group.getStudentsCount());
            insertStudyGroupStatement.setInt(6, group.getShouldBeExpelled());
            insertStudyGroupStatement.setDouble(7, group.getAverageMark());
            insertStudyGroupStatement.setString(8, group.getFormOfEducation().toString());
            insertStudyGroupStatement.setString(9, group.getGroupAdmin().getName());
            insertStudyGroupStatement.setDouble(10, group.getGroupAdmin().getHeight());
            insertStudyGroupStatement.setString(11, getString(group.getGroupAdmin().getEyeColor()));
            insertStudyGroupStatement.setString(12, getString(group.getGroupAdmin().getNationality()));
            insertStudyGroupStatement.setLong(13, group.getId());
            insertStudyGroupStatement.setInt(14, getUserIdByUsername(username));
        }

        int rowsAffected = insertStudyGroupStatement.executeUpdate();
        return rowsAffected > 0;
    }

    private String getString(Enum a) {
        return a.name();
    }

    @Override
    public boolean updateStudyGroup(Long id, StudyGroup group, String username) {
        try {
            if (!isGroupOwner(group.getId(), username)) {
                return false; // User is not the owner of the group, cannot update it
            }
            return setStatement(group, username, updateStudyGroupStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeStudyGroup(StudyGroup group, String username) {
        try {
            if (!isGroupOwner(group.getId(), username)) {
                return false; // User is not the owner of the group, cannot remove it
            }
            removeStudyGroupStatement.setLong(1, group.getId());
            removeStudyGroupStatement.setInt(2, getUserIdByUsername(username));
            int rowsAffected = removeStudyGroupStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection getStudyGroups() {
        try {
            ResultSet resultSet = getStudyGroupsStatement.executeQuery();
            PriorityQueue<StudyGroup> studyGroups = new PriorityQueue<>();
            while (resultSet.next()) {
                StudyGroup group = new StudyGroup(resultSet.getLong("id"));
                group.setName(resultSet.getString("name"));
                Coordinates coordinates = new Coordinates();
                coordinates.setX((double) resultSet.getFloat("coordinates_x"));
                coordinates.setY(resultSet.getFloat("coordinates_y"));
                group.setCoordinates(coordinates);
                group.setCreationDate(resultSet.getDate("creation_date").toLocalDate());
                group.setStudentsCount(resultSet.getInt("students_count"));
                group.setShouldBeExpelled(resultSet.getInt("should_be_expelled"));
                group.setAverageMark(resultSet.getLong("average_mark"));
                group.setFormOfEducation(FormOfEducation.valueOf(resultSet.getString("form_of_education")));
                Person groupAdmin = new Person();
                groupAdmin.setName(resultSet.getString("group_admin_name"));
                groupAdmin.setHeight(resultSet.getDouble("group_admin_height"));
                groupAdmin.setEyeColor(getColor(resultSet.getString("group_admin_eye_color")));
                groupAdmin.setNationality(getCountry(resultSet.getString("group_admin_nationality")));
                group.setGroupAdmin(groupAdmin);
                studyGroups.add(group);
            }
            return studyGroups;
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e){
            return null;
        }
        return null;
    }

    public static <T extends Enum<T>> T stringToEnum(String value, Class<T> enumClass) {
        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for enum " + enumClass.getSimpleName() + ": " + value);
        }
    }

    private Color getColor(String value) {
        return stringToEnum(value, Color.class);
    }

    private Country getCountry(String value) {
        return stringToEnum(value, Country.class);
    }

    private int getUserIdByUsername(String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM users WHERE name = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return -1;
    }

    private boolean isGroupOwner(long groupId, String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM groups WHERE id = ? AND user_id = ?");
        statement.setLong(1, groupId);
        statement.setInt(2, getUserIdByUsername(username));
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    private String hashAndSaltPassword(String password) {
        // Implement your password hashing and salting logic here
        // This is just a placeholder
        System.out.println("password - " + calculateSHA1(password));
        return calculateSHA1(password);
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String calculateSHA1(String input) {
        try {
            MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
            byte[] sha1Bytes = sha1Digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexBuilder = new StringBuilder();
            for (byte b : sha1Bytes) {
                hexBuilder.append(String.format("%02x", b));
            }
            return hexBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
