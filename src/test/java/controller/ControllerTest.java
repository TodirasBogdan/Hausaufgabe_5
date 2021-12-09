package controller;

import model.Course;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CourseJDBCRepository;
import repository.EnrolledJDBCRepository;
import repository.StudentJDBCRepository;
import repository.TeacherJDBCRepository;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTest {

    static CourseJDBCRepository courseJDBCRepository = new CourseJDBCRepository();
    static StudentJDBCRepository studentJDBCRepository = new StudentJDBCRepository();
    static TeacherJDBCRepository teacherJDBCRepository = new TeacherJDBCRepository();
    static EnrolledJDBCRepository enrolledJDBCRepository = new EnrolledJDBCRepository();
    Controller controller = new Controller(courseJDBCRepository, studentJDBCRepository, teacherJDBCRepository, enrolledJDBCRepository);

    static Course course1 = new Course(100, "Algebra", 10, 5, 70);
    static Course course2 = new Course(101, "Geometrie", 10, 4, 60);
    static Student student1 = new Student("Viorel", "Curecheriu", 1, 30);
    static Student student2 = new Student("Dorel", "Lob", 2, 20);
    static Student student3 = new Student("Victor", "Grigore", 3, 25);
    static Teacher teacher1 = new Teacher("Johann", "Klaus", 10);
    static Teacher teacher2 = new Teacher("Markus", "Aureli's", 11);

    /**
     * sets courseJDBCRepository, studentJDBCRepository, teacherJDBCRepository, enrolledJDBCRepository
     */
    @BeforeEach
    void setUp() throws IOException {
        courseJDBCRepository.save(course1);
        courseJDBCRepository.delete(course2);
        studentJDBCRepository.save(student1);
        studentJDBCRepository.save(student2);
        studentJDBCRepository.delete(student3);
        teacherJDBCRepository.save(teacher1);
        teacherJDBCRepository.delete(teacher2);
        enrolledJDBCRepository.delete(student1.getStudentId(), course1.getCourseId());
    }

    /**
     * deletes all from courseJDBCRepository, studentJDBCRepository, teacherJDBCRepository, enrolledJDBCRepository
     */
    @AfterAll
    static void cleanUp() throws IOException {
        courseJDBCRepository.delete(course1);
        courseJDBCRepository.delete(course2);
        studentJDBCRepository.delete(student1);
        studentJDBCRepository.delete(student2);
        studentJDBCRepository.delete(student3);
        teacherJDBCRepository.delete(teacher1);
        teacherJDBCRepository.delete(teacher2);
        enrolledJDBCRepository.delete(student1.getStudentId(), course1.getCourseId());
    }

    /**
     * check if register works properly
     */
    @Test
    void testRegister() throws IOException {
        assertEquals(controller.findAllCourses().size(), 1);
        assertEquals(controller.findAllStudents().size(), 2);
        assertTrue(controller.register(student1, course1));
    }

    /**
     * check if addStudent works properly
     */
    @Test
    void testAddStudent() throws IOException {
        assertEquals(controller.findAllStudents().size(), 2);
        controller.addStudent("Victor", "Grigore", 3, 25);
        assertEquals(controller.findAllStudents().size(), 3);
    }

    /**
     * check if findAllStudents works properly
     */
    @Test
    void testFindAllStudents() throws IOException {
        assertEquals(controller.findAllStudents().size(), 2);
        assertEquals(controller.findAllStudents().get(0), student1);
        assertEquals(controller.findAllStudents().get(1), student2);
    }

    /**
     * check if updateStudent works properly
     */
    @Test
    void testUpdateStudent() throws IOException {
        assertEquals(controller.findAllStudents().size(), 2);
        controller.addStudent("Victor", "Grigore", 3, 25);
        assertEquals(controller.findAllStudents().size(), 3);
        controller.updateStudent("Victor", "Grigore", 3, 5555);
        assertEquals(controller.findAllStudents().size(), 3);
        assertEquals(controller.findAllStudents().get(2).getStudentId(), 3);
        assertEquals(controller.findAllStudents().get(2).getTotalCredits(), 5555);
    }

    /**
     * check if deleteStudent works properly
     */
    @Test
    void testDeleteStudent() throws IOException {
        assertEquals(controller.findAllStudents().size(), 2);
        controller.addStudent("Victor", "Grigore", 3, 25);
        assertEquals(controller.findAllStudents().size(), 3);
        controller.deleteStudent(student3);
        assertEquals(controller.findAllStudents().size(), 2);
    }

    /**
     * check if sortStudentsByCredits works properly
     */
    @Test
    void testSortStudentsByCredits() throws IOException {
        assertEquals(controller.findAllStudents().size(), 2);
        assertEquals(controller.sortStudentsByCredits().get(0), student2);
        assertEquals(controller.sortStudentsByCredits().get(1), student1);
        assertEquals(controller.findAllStudents().size(), 2);
    }

    /**
     * check if filterStudentsByCredits works properly
     */
    @Test
    void testFilterStudentsByCredits() throws IOException {
        assertEquals(controller.findAllStudents().size(), 2);
        assertEquals(controller.filterStudentsByCredits().size(), 1);
        assertEquals(controller.filterStudentsByCredits().get(0), student2);
        assertEquals(controller.findAllStudents().size(), 2);
    }

    /**
     * check if addCourse works properly
     */
    @Test
    void testAddCourse() throws IOException {
        assertEquals(controller.findAllCourses().size(), 1);
        controller.addCourse(101, "Geometrie", 10, 6, 60);
        assertEquals(controller.findAllCourses().size(), 2);
    }

    /**
     * check if findAllCourses works properly
     */
    @Test
    void testFindAllCourses() throws IOException {
        assertEquals(controller.findAllCourses().size(), 1);
        assertEquals(controller.findAllCourses().get(0), course1);
    }

    /**
     * check if updateCourse works properly
     */
    @Test
    void testUpdateCourse() throws IOException {
        assertEquals(controller.findAllCourses().size(), 1);
        controller.addCourse(101, "Geometrie", 10, 6, 60);
        assertEquals(controller.findAllCourses().size(), 2);
        controller.updateCourse(101, "Geometrie", 10, 6, 70);
        assertEquals(controller.findAllCourses().size(), 2);
        assertEquals(controller.findAllCourses().get(1).getCourseId(), 101);
        assertEquals(controller.findAllCourses().get(1).getMaxEnrollment(), 70);
    }

    /**
     * check if deleteCourse works properly
     */
    @Test
    void testDeleteCourse() throws IOException {
        assertEquals(controller.findAllCourses().size(), 1);
        controller.addCourse(101, "Geometrie", 10, 6, 60);
        assertEquals(controller.findAllCourses().size(), 2);
        controller.deleteCourse(course2);
        assertEquals(controller.findAllCourses().size(), 1);
    }

    /**
     * check if sortCoursesByCredits works properly
     */
    @Test
    void testSortCoursesByCredits() throws IOException {
        controller.addCourse(101, "Geometrie", 10, 4, 60);
        assertEquals(controller.findAllCourses().size(), 2);
        assertEquals(controller.sortCoursesByCredits().get(0), course2);
        assertEquals(controller.sortCoursesByCredits().get(1), course1);
        assertEquals(controller.findAllCourses().size(), 2);
    }

    /**
     * check if filterCoursesByCredits works properly
     */
    @Test
    void testFilterCoursesByCredits() throws IOException {
        controller.addCourse(101, "Geometrie", 10, 4, 60);
        assertEquals(controller.findAllCourses().size(), 2);
        assertEquals(controller.filterCoursesByCredit(5).size(), 1);
        assertEquals(controller.filterCoursesByCredit(5).get(0), course2);
        assertEquals(controller.findAllCourses().size(), 2);
    }

    /**
     * check if addTeacher works properly
     */
    @Test
    void testAddTeacher() throws IOException {
        assertEquals(controller.findAllTeachers().size(), 1);
        controller.addTeacher("Markus", "Aureli's", 11);
        assertEquals(controller.findAllTeachers().size(), 2);
    }

    /**
     * check if findAllTeachers works properly
     */
    @Test
    void testFindAllTeachers() throws IOException {
        assertEquals(controller.findAllTeachers().size(), 1);
        assertEquals(controller.findAllTeachers().get(0), teacher1);
    }

    /**
     * check if updateTeacher works properly
     */
    @Test
    void testUpdateTeacher() throws IOException {
        assertEquals(controller.findAllTeachers().size(), 1);
        controller.addTeacher("Markus", "Aureli's", 11);
        assertEquals(controller.findAllTeachers().size(), 2);
        controller.updateTeacher("Markus", "Aurelius", 11);
        assertEquals(controller.findAllTeachers().size(), 2);
        assertEquals(controller.findAllTeachers().get(1).getTeacherId(), 11);
        assertEquals(controller.findAllTeachers().get(1).getLastName(), "Aurelius");
    }

    /**
     * check if deleteTeacher works properly
     */
    @Test
    void testDeleteTeacher() throws IOException {
        assertEquals(controller.findAllTeachers().size(), 1);
        controller.addTeacher("Markus", "Aureli's", 11);
        assertEquals(controller.findAllTeachers().size(), 2);
        controller.deleteTeacher(teacher2);
        assertEquals(controller.findAllTeachers().size(), 1);
    }
}