package model;

import java.util.Objects;

public class Student extends Person{

    private int studentId;
    private int totalCredits;

    public Student(String firstName, String lastName, int studentId, int totalCredits) {
        super(firstName, lastName);
        this.studentId = studentId;
        this.totalCredits = totalCredits;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studentId=" + studentId +
                ", totalCredits=" + totalCredits +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        if (!super.equals(o)) return false;
        return getStudentId() == student.getStudentId() && getTotalCredits() == student.getTotalCredits();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStudentId(), getTotalCredits());
    }
}
