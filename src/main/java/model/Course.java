package model;

import java.util.Objects;

public class Course {

    private int courseId;
    private String name;
    private int teacherId;
    private int credits;
    private int maxEnrollment;

    public Course(int courseId, String name, int teacherId, int credits, int maxEnrollment) {
        this.courseId = courseId;
        this.name = name;
        this.teacherId = teacherId;
        this.credits = credits;
        this.maxEnrollment = maxEnrollment;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return getCourseId() == course.getCourseId() && getCredits() == course.getCredits() && getMaxEnrollment() == course.getMaxEnrollment() && Objects.equals(getName(), course.getName()) && Objects.equals(getTeacherId(), course.getTeacherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(), getName(), getTeacherId(), getCredits(), getMaxEnrollment());
    }

}
