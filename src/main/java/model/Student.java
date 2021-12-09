package model;

import java.util.Objects;

public class Student extends Person {

    private final int studentId;
    private final int totalCredits;

    public Student(String firstName, String lastName, int studentId, int totalCredits) {
        super(firstName, lastName);
        this.studentId = studentId;
        this.totalCredits = totalCredits;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getTotalCredits() {
        return totalCredits;
    }


    /**
     * return student and their details as a string
     */
    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studentId=" + studentId +
                ", totalCredits=" + totalCredits +
                '}';
    }

    /**
     * check if two students are equal
     *
     * @param o is a student object
     * @return true if two students are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        if (!super.equals(o)) return false;
        return getStudentId() == student.getStudentId() && getTotalCredits() == student.getTotalCredits();
    }

    /**
     * return a hashcode of student
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStudentId(), getTotalCredits());
    }

}
