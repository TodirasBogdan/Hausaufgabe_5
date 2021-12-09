package controller;

import model.Course;
import model.Student;
import model.Teacher;
import repository.CourseJDBCRepository;
import repository.EnrolledJDBCRepository;
import repository.StudentJDBCRepository;
import repository.TeacherJDBCRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record Controller(CourseJDBCRepository courseJDBCRepository, StudentJDBCRepository studentJDBCRepository,
                         TeacherJDBCRepository teacherJDBCRepository, EnrolledJDBCRepository enrolledJDBCRepository) {

    /**
     * create a course and add it to courseJDBCRepository
     */
    public void addCourse(int courseId, String name, int teacherId, int credits, int maxEnrollment) throws IOException {
        Course course = new Course(courseId, name, teacherId, credits, maxEnrollment);
        this.courseJDBCRepository.save(course);
    }

    /**
     * create a student and add it to studentJDBCRepository
     */
    public void addStudent(String firstName, String lastName, int studentId, int totalCredits) throws IOException {
        Student student = new Student(firstName, lastName, studentId, totalCredits);
        this.studentJDBCRepository.save(student);
    }

    /**
     * create a teacher and add it to teacherJDBCRepository
     */
    public void addTeacher(String firstName, String lastName, int teacherId) throws IOException {
        Teacher teacher = new Teacher(firstName, lastName, teacherId);
        this.teacherJDBCRepository.save(teacher);
    }


    /**
     * create a student and a course and add their Ids to enrolledJDBCRepository
     */
    public boolean register(Student student, Course course) throws IOException {
        Student foundStudent = this.studentJDBCRepository.findOne(student);
        Course foundCourse = this.courseJDBCRepository.findOne(course);
        this.enrolledJDBCRepository.save(foundStudent.getStudentId(), foundCourse.getCourseId());
        return true;
    }


    /**
     * return all courses from courseJDBCRepository as a list
     */
    public ArrayList<Course> findAllCourses() throws IOException {
        return (ArrayList<Course>) courseJDBCRepository.findAll();
    }

    /**
     * return all students from studentJDBCRepository as a list
     */
    public ArrayList<Student> findAllStudents() throws IOException {
        return (ArrayList<Student>) studentJDBCRepository.findAll();
    }

    /**
     * return all teachers from teacherJDBCRepository as a list
     */
    public ArrayList<Teacher> findAllTeachers() throws IOException {
        return (ArrayList<Teacher>) teacherJDBCRepository.findAll();
    }

    /**
     * update a course from courseJDBCRepository
     */
    public void updateCourse(int courseId, String name, int teacherId, int credits, int maxEnrollment) throws IOException {
        Course course = new Course(courseId, name, teacherId, credits, maxEnrollment);
        this.courseJDBCRepository.update(course);
    }

    /**
     * update a student from studentJDBCRepository
     */
    public void updateStudent(String firstName, String lastName, int studentId, int totalCredits) throws IOException {
        Student student = new Student(firstName, lastName, studentId, totalCredits);
        this.studentJDBCRepository.update(student);
    }

    /**
     * update a teacher from teacherJDBCRepository
     */
    public void updateTeacher(String firstName, String lastName, int teacherId) throws IOException {
        Teacher teacher = new Teacher(firstName, lastName, teacherId);
        this.teacherJDBCRepository.update(teacher);
    }

    /**
     * delete a course from courseJDBCRepository
     */
    public void deleteCourse(Course course) throws IOException {
        Course foundCourse = this.courseJDBCRepository.findOne(course);
        this.enrolledJDBCRepository.deleteAllStudentsFromCourse(foundCourse.getCourseId());
        this.courseJDBCRepository.delete(foundCourse);
    }

    /**
     * delete a student from studentJDBCRepository
     */
    public void deleteStudent(Student student) throws IOException {
        Student foundStudent = this.studentJDBCRepository.delete(student);
        this.enrolledJDBCRepository.deleteAllCoursesFromStudent(foundStudent.getStudentId());
        this.studentJDBCRepository.delete(foundStudent);
    }

    /**
     * delete a teacher from teacherJDBCRepository
     */
    public void deleteTeacher(Teacher teacher) throws IOException {
        Teacher foundTeacher = this.teacherJDBCRepository.findOne(teacher);
        ArrayList<Course> courses = (ArrayList<Course>) this.courseJDBCRepository.findAll();
        for (Course c : courses) {
            if (c.getTeacherId() == foundTeacher.getTeacherId()) {
                this.deleteCourse(c);
            }
        }
        this.teacherJDBCRepository.delete(foundTeacher);
    }


    /**
     * sorts all students by credits
     *
     * @return a sorted list of students
     */
    public ArrayList<Student> sortStudentsByCredits() throws IOException {
        ArrayList<Student> students = (ArrayList<Student>) studentJDBCRepository.findAll();
        return (ArrayList<Student>) students.stream()
                .sorted(Comparator.comparingInt(Student::getTotalCredits))
                .collect(Collectors.toList());
    }

    /**
     * sorts all courses by credits
     *
     * @return a sorted list of courses
     */
    public ArrayList<Course> sortCoursesByCredits() throws IOException {
        ArrayList<Course> courses = (ArrayList<Course>) courseJDBCRepository.findAll();
        return (ArrayList<Course>) courses.stream()
                .sorted(Comparator.comparingInt(Course::getCredits))
                .collect(Collectors.toList());
    }

    /**
     * filters all students whose credits < 25
     *
     * @return a filtered list of students
     */
    public ArrayList<Student> filterStudentsByCredits() throws IOException {
        Predicate<Student> byCredits = student -> student.getTotalCredits() < 25;
        ArrayList<Student> students = (ArrayList<Student>) studentJDBCRepository.findAll();
        return (ArrayList<Student>) students.stream().filter(byCredits).collect(Collectors.toList());
    }

    /**
     * filters all courses by credits < parameter credits
     *
     * @param credits is number of credits to be compared with
     * @return a filtered list of courses
     */
    public ArrayList<Course> filterCoursesByCredit(int credits) throws IOException {
        Predicate<Course> byCredits = course -> course.getCredits() < credits;
        ArrayList<Course> courses = (ArrayList<Course>) courseJDBCRepository.findAll();
        return (ArrayList<Course>) courses.stream().filter(byCredits).collect(Collectors.toList());
    }
}
