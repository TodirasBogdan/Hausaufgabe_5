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

public record Controller(CourseJDBCRepository crepo, StudentJDBCRepository srepo, TeacherJDBCRepository trepo, EnrolledJDBCRepository erepo) {

    public boolean addCourse(int courseId, String name, int teacherId, int credits, int maxEnrollment) throws IOException {

        Course c = new Course(courseId, name, teacherId, credits, maxEnrollment);
        this.crepo.save(c);
        return true;
    }

    public boolean addStudent(String firstName, String lastName, int studentId, int totalCredits) throws IOException {
        Student s = new Student(firstName, lastName, studentId, totalCredits);
        this.srepo.save(s);
        return true;
    }

    public boolean addTeacher(String firstName, String lastName, int teacherId) throws IOException {
        Teacher teacher = new Teacher(firstName, lastName, teacherId);
        this.trepo.save(teacher);
        return true;
    }


    public boolean register(Student student, Course course) throws IOException {
        Student foundStudent = this.srepo.findOne(student);
        Course foundCourse = this.crepo.findOne(course);
        this.erepo.save(foundStudent.getStudentId(), foundCourse.getCourseId());
        return true;
    }

    public ArrayList<Course> findAllCourses() throws IOException {
        return (ArrayList<Course>) crepo.findAll();
    }

    public ArrayList<Student> findAllStudents() throws IOException {
        return (ArrayList<Student>) srepo.findAll();
    }

    public ArrayList<Teacher> findAllTeachers() throws IOException {
        return (ArrayList<Teacher>) trepo.findAll();
    }

    public boolean deleteCourse(Course course) throws IOException {
        Course foundCourse = this.crepo.findOne(course);
        this.erepo.deleteAllStudentsFromCourse(foundCourse.getCourseId());
        this.crepo.delete(foundCourse);
        return true;
    }

    public boolean deleteStudent(Student student) throws IOException {
        Student foundStudent = this.srepo.delete(student);
        this.erepo.deleteAllCoursesFromStudent(foundStudent.getStudentId());
        this.srepo.delete(foundStudent);
        return true;
    }


    public boolean deleteTeacher(Teacher teacher) throws IOException {
        Teacher foundTeacher = this.trepo.findOne(teacher);
        ArrayList<Course> courses = (ArrayList<Course>) this.crepo.findAll();
        for (Course c : courses) {
            if (c.getTeacherId() == foundTeacher.getTeacherId()) {
                this.deleteCourse(c);
            }
        }
        this.trepo.delete(foundTeacher);
        return true;
    }


    public ArrayList<Student> sortStudentsByCredits() throws IOException {
        ArrayList<Student> newlist = (ArrayList<Student>) srepo.findAll();
        return (ArrayList<Student>) newlist.stream()
                .sorted(Comparator.comparingInt(Student::getTotalCredits))
                .collect(Collectors.toList());
    }

    public ArrayList<Course> sortCoursesByCredits() throws IOException {
        ArrayList<Course> newlist = (ArrayList<Course>) crepo.findAll();
        return (ArrayList<Course>) newlist.stream()
                .sorted(Comparator.comparingInt(Course::getCredits))
                .collect(Collectors.toList());
    }

    public ArrayList<Student> filterStudentsByCredits() throws IOException {
        Predicate<Student> byCredits = student -> student.getTotalCredits() < 25;
        ArrayList<Student> newlist = (ArrayList<Student>) srepo.findAll();
        return (ArrayList<Student>) newlist.stream().filter(byCredits).collect(Collectors.toList());
    }

    public ArrayList<Course> filterCoursesByCredit(int credits) throws IOException {
        Predicate<Course> byCredits = course -> course.getCredits() < credits;
        ArrayList<Course> newlist = (ArrayList<Course>) crepo.findAll();
        return (ArrayList<Course>) newlist.stream().filter(byCredits).collect(Collectors.toList());
    }
}
