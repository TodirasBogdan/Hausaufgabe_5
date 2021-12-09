package model;

import java.util.Objects;

public class Person {

    protected String firstName;
    protected String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    /**
     * return person and their details as a string
     */
    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName +
                '}';
    }

    /**
     * check if two persons are equal
     *
     * @param o is a person object
     * @return true if two persons are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName());
    }

    /**
     * return a hashcode of person
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }
}
