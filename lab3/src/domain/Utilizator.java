package domain;

import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<Long> {
    //firstname
    private String firstName;
    //lastname
    private String lastName;
    private static long contor=0;

    /**
     * construnctor
     *
     * @param firstName String
     * @param lastName  String
     */
    public Utilizator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.setId(contor++);
    }

    /**
     * @return the firstname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * set the firstName
     *
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * return LastName
     *
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * set lastname
     *
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * override toString
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Utilizator{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                " id= " + id +
                '}';
    }

    /**
     * override equals
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName());

    }

    /**
     * override hashcode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }
}