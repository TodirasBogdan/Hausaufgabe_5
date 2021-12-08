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

    public void addCourse(int courseId, String name, int teacherId, int credits, int maxEnrollment) throws IOException {
        Course course = new Course(courseId, name, teacherId, credits, maxEnrollment);
        this.courseJDBCRepository.save(course);
    }

    public void addStudent(String firstName, String lastName, int studentId, int totalCredits) throws IOException {
        Student student = new Student(firstName, lastName, studentId, totalCredits);
        this.studentJDBCRepository.save(student);
    }

    public void addTeacher(String firstName, String lastName, int teacherId) throws IOException {
        Teacher teacher = new Teacher(firstName, lastName, teacherId);
        this.teacherJDBCRepository.save(teacher);
    }


    public boolean register(Student student, Course course) throws IOException {
        Student foundStudent = this.studentJDBCRepository.findOne(student);
        Course foundCourse = this.courseJDBCRepository.findOne(course);
        this.enrolledJDBCRepository.save(foundStudent.getStudentId(), foundCourse.getCourseId());
        return true;
    }


    public ArrayList<Course> findAllCourses() throws IOException {
        return (ArrayList<Course>) courseJDBCRepository.findAll();
    }

    public ArrayList<Student> findAllStudents() throws IOException {
        return (ArrayList<Student>) studentJDBCRepository.findAll();
    }

    public ArrayList<Teacher> findAllTeachers() throws IOException {
        return (ArrayList<Teacher>) teacherJDBCRepository.findAll();
    }

    public void updateCourse(int courseId, String name, int teacherId, int credits, int maxEnrollment) throws IOException {
        Course course = new Course(courseId, name, teacherId, credits, maxEnrollment);
        this.courseJDBCRepository.update(course);
    }

    public void updateStudent(String firstName, String lastName, int studentId, int totalCredits) throws IOException {
        Student student = new Student(firstName, lastName, studentId, totalCredits);
        this.studentJDBCRepository.update(student);
    }

    public void updateTeacher(String firstName, String lastName, int teacherId) throws IOException {
        Teacher teacher = new Teacher(firstName, lastName, teacherId);
        this.teacherJDBCRepository.update(teacher);
    }

    public void deleteCourse(Course course) throws IOException {
        Course foundCourse = this.courseJDBCRepository.findOne(course);
        this.enrolledJDBCRepository.deleteAllStudentsFromCourse(foundCourse.getCourseId());
        this.courseJDBCRepository.delete(foundCourse);
    }

    public void deleteStudent(Student student) throws IOException {
        Student foundStudent = this.studentJDBCRepository.delete(student);
        this.enrolledJDBCRepository.deleteAllCoursesFromStudent(foundStudent.getStudentId());
        this.studentJDBCRepository.delete(foundStudent);
    }

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


    public ArrayList<Student> sortStudentsByCredits() throws IOException {
        ArrayList<Student> students = (ArrayList<Student>) studentJDBCRepository.findAll();
        return (ArrayList<Student>) students.stream()
                .sorted(Comparator.comparingInt(Student::getTotalCredits))
                .collect(Collectors.toList());
    }

    public ArrayList<Course> sortCoursesByCredits() throws IOException {
        ArrayList<Course> courses = (ArrayList<Course>) courseJDBCRepository.findAll();
        return (ArrayList<Course>) courses.stream()
                .sorted(Comparator.comparingInt(Course::getCredits))
                .collect(Collectors.toList());
    }

    public ArrayList<Student> filterStudentsByCredits() throws IOException {
        Predicate<Student> byCredits = student -> student.getTotalCredits() < 25;
        ArrayList<Student> students = (ArrayList<Student>) studentJDBCRepository.findAll();
        return (ArrayList<Student>) students.stream().filter(byCredits).collect(Collectors.toList());
    }

    public ArrayList<Course> filterCoursesByCredit(int credits) throws IOException {
        Predicate<Course> byCredits = course -> course.getCredits() < credits;
        ArrayList<Course> courses = (ArrayList<Course>) courseJDBCRepository.findAll();
        return (ArrayList<Course>) courses.stream().filter(byCredits).collect(Collectors.toList());
    }
}
