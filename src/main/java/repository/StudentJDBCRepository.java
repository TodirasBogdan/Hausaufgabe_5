package repository;

import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentJDBCRepository implements ICrudRepository<Student> {

    private final String DB_URL = "jdbc:mysql://localhost:3306/hausaufgabe_5_db_mysql";
    private final String USER = "root";
    private final String PASS = "root";

    @Override
    public Student findOne(Student obj) {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            String QUERY = "SELECT studentId, firstName, lastName, totalCredits FROM student";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                if (obj.getStudentId() == resultSet.getInt("studentId")) {
                    return new Student(resultSet.getString("firstName"), resultSet.getString("lastName"),
                            resultSet.getInt("studentId"), resultSet.getInt("totalCredits"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Student> findAll() {

        List<Student> studentList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            String QUERY = "SELECT studentId, firstName, lastName, totalCredits FROM student";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                Student student = new Student(resultSet.getString("firstName"), resultSet.getString("lastName"),
                        resultSet.getInt("studentId"), resultSet.getInt("totalCredits"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    @Override
    public Student save(Student obj) {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student VALUES (?,?,?,?)")) {
            if (findOne(obj) == null) {
                preparedStatement.setInt(1, obj.getStudentId());
                preparedStatement.setString(2, obj.getFirstName());
                preparedStatement.setString(3, obj.getLastName());
                preparedStatement.setInt(4, obj.getTotalCredits());
                preparedStatement.executeUpdate();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Student update(Student obj) {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE student SET firstName = ?, lastName = ?, totalCredits = ? WHERE studentId = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setString(1, obj.getFirstName());
                preparedStatement.setString(2, obj.getLastName());
                preparedStatement.setInt(3, obj.getTotalCredits());
                preparedStatement.setInt(4, obj.getStudentId());
                preparedStatement.executeUpdate();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Student delete(Student obj) {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM student WHERE studentId = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setInt(1, obj.getStudentId());
                preparedStatement.executeUpdate();
                return obj;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
