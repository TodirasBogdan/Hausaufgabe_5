package view;

import controller.Controller;
import model.Course;
import model.Student;
import model.Teacher;

import java.io.IOException;
import java.util.Scanner;

public class UI {
    private final Controller controller;
    private final Scanner input = new Scanner(System.in);

    public UI(Controller controller) {
        this.controller = controller;
    }

    /**
     * read a course from user input and return it
     */
    public Course readCourse() {
        System.out.println("ID of the course:\n");
        int courseId = input.nextInt();
        System.out.println("Name of the course:\n");
        String name = input.next();
        System.out.println("ID of the teacher:\n");
        int teacherId = input.nextInt();
        System.out.println("Number of credits:\n");
        int credits = input.nextInt();
        System.out.println("Maximum number of students:\n");
        int maxEnrollment = input.nextInt();
        return new Course(courseId, name, teacherId, credits, maxEnrollment);
    }

    /**
     * read a student from user input and return it
     */
    public Student readStudent() {
        System.out.println("First name of the student:\n");
        String firstName = input.next();
        System.out.println("Last name of the student:\n");
        String lastName = input.next();
        System.out.println("Student ID:\n");
        int studentId = input.nextInt();
        System.out.println("Number of credits:\n");
        int totalCredits = input.nextInt();
        return new Student(firstName, lastName, studentId, totalCredits);
    }

    /**
     * read a teacher from user input and return it
     */
    public Teacher readTeacher() {
        System.out.println("First name of the teacher:\n");
        String firstName = input.next();
        System.out.println("Last name of the teacher:\n");
        String lastName = input.next();
        System.out.println("Teacher ID:\n");
        int tID = input.nextInt();
        return new Teacher(firstName, lastName, tID);
    }

    /**
     * function display shows the options of the app in the console
     * and uses a switch command to decide which methods of the controller to call;
     * the user must choose a number between 1-17 or 18 to exit the app
     */
    public void display() throws IOException {
        while (true) {
            System.out.println("""
                    Please choose an option:\s
                    1. Add a course\s
                    2. Add a student\s
                    3. Add a teacher\s
                    4. Register a student to a course\s
                    5. Display all courses\s
                    6. Display all students\s
                    7. Display all teachers\s
                    8. Update a course\s
                    9. Update a student\s
                    10. Update a teacher\s
                    11. Delete a course\s
                    12. Delete a student\s
                    13. Delete a teacher\s
                    14. Sort the courses by credits\s
                    15. Display the courses with less than a given number of credits\s
                    16. Sort the students by credits\s
                    17. Display the students with less than 15 credits (filter)\s
                    18. Exit\s
                    """
            );
            int option = input.nextInt();
            int token = 0;
            switch (option) {
                case 1:
                    System.out.println("ID of the course:\n");
                    int courseId = input.nextInt();
                    System.out.println("Name of the course:\n");
                    String name = input.next();
                    System.out.println("ID of the teacher:\n");
                    int teacherId = input.nextInt();
                    System.out.println("Number of credits:\n");
                    int credits = input.nextInt();
                    System.out.println("Maximum number of students:\n");
                    int maxEnrollment = input.nextInt();
                    controller.addCourse(courseId, name, teacherId, credits, maxEnrollment);
                    break;
                case 2:
                    System.out.println("First name of the student:\n");
                    String firstName = input.next();
                    System.out.println("Last name of the student:\n");
                    String lastName = input.next();
                    System.out.println("Student ID:\n");
                    int studentId = input.nextInt();
                    System.out.println("Number of credits:\n");
                    int totalCredits = input.nextInt();
                    controller.addStudent(firstName, lastName, studentId, totalCredits);
                    break;
                case 3:
                    System.out.println("First name of the teacher:\n");
                    firstName = input.next();
                    System.out.println("Last name of the teacher:\n");
                    lastName = input.next();
                    System.out.println("Teacher ID:\n");
                    teacherId = input.nextInt();
                    controller.addTeacher(firstName, lastName, teacherId);
                    break;
                case 4:
                    this.controller.register(readStudent(), readCourse());
                    break;
                case 5:
                    System.out.println(controller.findAllCourses());
                    break;
                case 6:
                    System.out.println(controller.findAllStudents());
                    break;
                case 7:
                    System.out.println(controller.findAllTeachers());
                    break;
                case 8:
                    System.out.println("ID of the course:\n");
                    courseId = input.nextInt();
                    System.out.println("Name of the course:\n");
                    name = input.next();
                    System.out.println("ID of the teacher:\n");
                    teacherId = input.nextInt();
                    System.out.println("Number of credits:\n");
                    credits = input.nextInt();
                    System.out.println("Maximum number of students:\n");
                    maxEnrollment = input.nextInt();
                    controller.updateCourse(courseId, name, teacherId, credits, maxEnrollment);
                    break;
                case 9:
                    System.out.println("First name of the student:\n");
                    firstName = input.next();
                    System.out.println("Last name of the student:\n");
                    lastName = input.next();
                    System.out.println("Student ID:\n");
                    studentId = input.nextInt();
                    System.out.println("Number of credits:\n");
                    totalCredits = input.nextInt();
                    controller.updateStudent(firstName, lastName, studentId, totalCredits);
                    break;
                case 10:
                    System.out.println("First name of the teacher:\n");
                    firstName = input.next();
                    System.out.println("Last name of the teacher:\n");
                    lastName = input.next();
                    System.out.println("Teacher ID:\n");
                    teacherId = input.nextInt();
                    controller.updateTeacher(firstName, lastName, teacherId);
                    break;
                case 11:
                    controller.deleteCourse(this.readCourse());
                    break;
                case 12:
                    controller.deleteStudent(this.readStudent());
                    break;
                case 13:
                    controller.deleteTeacher(this.readTeacher());
                    break;
                case 14:
                    System.out.println(controller.sortCoursesByCredits());
                    break;
                case 15:
                    System.out.println("Please write the number of credits:\n");
                    credits = input.nextInt();
                    System.out.println(controller.filterCoursesByCredit(credits));
                    break;
                case 16:
                    System.out.println(controller.sortStudentsByCredits());
                    break;
                case 17:
                    System.out.println(controller.filterStudentsByCredits());
                    break;
                case 18:
                    token = 1;
                    break;
            }
            if (token == 1) break;
        }
    }
}
