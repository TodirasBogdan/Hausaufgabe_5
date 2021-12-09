package repository;

import model.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


public class StudentJDBCRepository implements ICrudRepository<Student> {

    private String db_url;
    private String user;
    private String password;

    /**
     * opens connection to mysql using config.properties file
     */
    public void openConnection() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\Downloads\\LECTII UBB\\An_2 Semestrul_1\\Metode Avansate de Programare\\Laborator\\Hausaufgabe_5\\src\\main\\resources\\config.properties");
        Properties prop = new Properties();
        prop.load(fis);
        db_url = prop.getProperty("db_url");
        user = prop.getProperty("user");
        password = prop.getProperty("password");
    }

    /**
     * closes connection with mysql
     */
    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }


    /**
     * implement findOne function from ICrudRepository<Student>
     */
    @Override
    public Student findOne(Student obj) throws IOException {

        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); Statement statement = connection.createStatement()) {
            String QUERY = "SELECT studentId, firstName, lastName, totalCredits FROM student";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                if (obj.getStudentId() == resultSet.getInt("studentId")) {
                    closeConnection(connection);
                    return obj;
                }
            }

            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * implement findAll function from ICrudRepository<Student>
     */
    @Override
    public Iterable<Student> findAll() throws IOException {

        openConnection();
        ArrayList<Student> studentList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(db_url, user, password); Statement statement = connection.createStatement()) {
            String QUERY = "SELECT studentId, firstName, lastName, totalCredits FROM student";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                Student student = new Student(resultSet.getString("firstName"), resultSet.getString("lastName"),
                        resultSet.getInt("studentId"), resultSet.getInt("totalCredits"));
                studentList.add(student);
            }

            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    /**
     * implement save function from ICrudRepository<Student>
     */
    @Override
    public void save(Student obj) throws IOException {

        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student VALUES (?,?,?,?)")) {
            if (findOne(obj) == null) {
                preparedStatement.setInt(1, obj.getStudentId());
                preparedStatement.setString(2, obj.getFirstName());
                preparedStatement.setString(3, obj.getLastName());
                preparedStatement.setInt(4, obj.getTotalCredits());
                preparedStatement.executeUpdate();
            }

            closeConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * implement update function from ICrudRepository<Student>
     */
    @Override
    public void update(Student obj) throws IOException {

        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE student SET firstName = ?, lastName = ?, totalCredits = ? WHERE studentId = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setString(1, obj.getFirstName());
                preparedStatement.setString(2, obj.getLastName());
                preparedStatement.setInt(3, obj.getTotalCredits());
                preparedStatement.setInt(4, obj.getStudentId());
                preparedStatement.executeUpdate();
            }

            closeConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * implement delete function from ICrudRepository<Student>
     */
    @Override
    public Student delete(Student obj) throws IOException {

        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM student WHERE studentId = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setInt(1, obj.getStudentId());
                preparedStatement.executeUpdate();
                closeConnection(connection);
                return obj;
            }

            closeConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
