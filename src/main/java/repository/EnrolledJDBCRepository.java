package repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class EnrolledJDBCRepository {

    private String DB_URL;
    private String USER;
    private String PASS;

    public void openConnection() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\Downloads\\LECTII UBB\\An_2 Semestrul_1\\Metode Avansate de Programare\\Laborator\\Hausaufgabe_5\\src\\main\\resources\\config.properties");
        Properties prop = new Properties();
        prop.load(fis);
        DB_URL = prop.getProperty("DB_URL");
        USER = prop.getProperty("USER");
        PASS = prop.getProperty("PASS");
    }

    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public ArrayList<Integer> findCoursesByStudentId(int studentId) throws IOException {
        openConnection();
        ArrayList<Integer> coursesIds = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            String QUERY = "SELECT studentId, courseId FROM enrolled";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                if (resultSet.getInt("studentId") == studentId) {
                    coursesIds.add(resultSet.getInt("courseId"));
                }
            }

            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesIds;
    }

    public ArrayList<Integer> findStudentsByCourseId(int courseId) throws IOException {
        openConnection();
        ArrayList<Integer> studentsIds = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            String QUERY = "SELECT studentId, courseId FROM enrolled";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                if (resultSet.getInt("courseId") == courseId) {
                    studentsIds.add(resultSet.getInt("studentId"));
                }
            }

            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentsIds;
    }

    public void save(int studentId, int courseId) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO enrolled VALUES (?,?)")) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int studentId, int courseId) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM enrolled WHERE studentId = ? AND courseId = ?")) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllStudentsFromCourse(int courseId) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM enrolled WHERE courseId = ?")) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.executeUpdate();
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllCoursesFromStudent(int studentId) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM enrolled WHERE studentId = ?")) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
