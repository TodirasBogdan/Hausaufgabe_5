package main;

import controller.Controller;
import repository.CourseJDBCRepository;
import repository.EnrolledJDBCRepository;
import repository.StudentJDBCRepository;
import repository.TeacherJDBCRepository;
import view.UI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        CourseJDBCRepository courseJDBCRepository = new CourseJDBCRepository();
        StudentJDBCRepository studentJDBCRepository = new StudentJDBCRepository();
        TeacherJDBCRepository teacherJDBCRepository = new TeacherJDBCRepository();
        EnrolledJDBCRepository enrolledJDBCRepository = new EnrolledJDBCRepository();
        Controller controller = new Controller(courseJDBCRepository,studentJDBCRepository,teacherJDBCRepository,enrolledJDBCRepository);
        UI ui = new UI(controller);

        ui.display();
    }
}
