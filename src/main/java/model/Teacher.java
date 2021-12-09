package model;

import java.util.Objects;

public class Teacher extends Person {

    private final int teacherId;

    public Teacher(String firstName, String lastName, int teacherId) {
        super(firstName, lastName);
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }


    /**
     * return teacher and their details as a string
     */
    @Override
    public String toString() {
        return "Teacher{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", teacherId=" + teacherId +
                '}';
    }

    /**
     * check if two teachers are equal
     *
     * @param o is a teacher object
     * @return true if two teachers are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher teacher)) return false;
        if (!super.equals(o)) return false;
        return getTeacherId() == teacher.getTeacherId();
    }

    /**
     * return a hashcode of teacher
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTeacherId());
    }
}
