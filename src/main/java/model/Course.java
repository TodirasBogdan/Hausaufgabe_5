package model;

import java.util.Objects;

public record Course(int courseId, String name, int teacherId, int credits, int maxEnrollment) {

    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getCredits() {
        return credits;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }


    /**
     * return course and its details as a string
     */
    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", teacherId=" + teacherId +
                ", credits=" + credits +
                ", maxEnrollment=" + maxEnrollment +
                '}';
    }

    /**
     * check if two courses are equal
     *
     * @param o is a course object
     * @return true if two courses are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return getCourseId() == course.getCourseId() && getCredits() == course.getCredits() && getMaxEnrollment() == course.getMaxEnrollment() && Objects.equals(getName(), course.getName()) && Objects.equals(getTeacherId(), course.getTeacherId());
    }

    /**
     * return a hashcode of course
     */
    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(), getName(), getTeacherId(), getCredits(), getMaxEnrollment());
    }

}
