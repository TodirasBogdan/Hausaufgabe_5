package repository;

import model.Course;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CourseJDBCRepository implements ICrudRepository<Course> {

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
     * implement findOne function from ICrudRepository<Course>
     */
    @Override
    public Course findOne(Course obj) throws IOException {

        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); Statement statement = connection.createStatement()) {
            String QUERY = "SELECT courseId, name, teacherId, credits, maxEnrollment FROM course";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                if (obj.getCourseId() == resultSet.getInt("courseId")) {
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
     * implement findAll function from ICrudRepository<Course>
     */
    @Override
    public Iterable<Course> findAll() throws IOException {

        openConnection();
        List<Course> courseList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(db_url, user, password); Statement statement = connection.createStatement()) {
            String QUERY = "SELECT courseId, name, teacherId, credits, maxEnrollment FROM course";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                Course course = new Course(resultSet.getInt("courseId"), resultSet.getString("name"), resultSet.getInt("teacherId"), resultSet.getInt("credits"), resultSet.getInt("maxEnrollment"));
                courseList.add(course);
            }

            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    /**
     * implement save function from ICrudRepository<Course>
     */
    @Override
    public void save(Course obj) throws IOException {

        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO course VALUES (?,?,?,?,?)")) {
            if (findOne(obj) == null) {
                preparedStatement.setInt(1, obj.getCourseId());
                preparedStatement.setString(2, obj.getName());
                preparedStatement.setInt(3, obj.getTeacherId());
                preparedStatement.setInt(4, obj.getCredits());
                preparedStatement.setInt(5, obj.getMaxEnrollment());
                preparedStatement.executeUpdate();
            }

            closeConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * implement update function from ICrudRepository<Course>
     */
    @Override
    public void update(Course obj) throws IOException {

        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE course SET name = ?, teacherId = ?, credits = ?, maxEnrollment = ? WHERE courseId = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setString(1, obj.getName());
                preparedStatement.setInt(2, obj.getTeacherId());
                preparedStatement.setInt(3, obj.getCredits());
                preparedStatement.setInt(4, obj.getMaxEnrollment());
                preparedStatement.setInt(5, obj.getCourseId());
                preparedStatement.executeUpdate();
            }

            closeConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * implement delete function from ICrudRepository<Course>
     */
    @Override
    public Course delete(Course obj) throws IOException {

        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM course WHERE courseId = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setInt(1, obj.getCourseId());
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
