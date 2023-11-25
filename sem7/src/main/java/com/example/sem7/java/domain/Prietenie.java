package com.example.sem7.java.domain;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;


public class Prietenie extends Entity<Tuple<Long, Long>> {
    //data la care prietenia a inceput
    LocalDateTime date;
    //id primul prieten
    private long idPrieten1;
    //id al doilea prieten
    private long idPrieten2;

    /**
     * ovverride toString
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Prietenie{" +
                "date=" + date +
                ", idPrieten1=" + idPrieten1 +
                ", idPrieten2=" + idPrieten2 +
                " id:" + id +
                '}';
    }

    /**
     * construnctor Prietenie
     *
     * @param date       LocalDateTime
     * @param idPrieten1 long
     * @param idPrieten2 long
     */
    public Prietenie(LocalDateTime date, long idPrieten1, long idPrieten2) {
        this.date = date;
        this.idPrieten1 = idPrieten1;
        this.idPrieten2 = idPrieten2;
        this.setId(new Tuple<>(idPrieten1, idPrieten2));
    }


    /**
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }
    public Month getMonth(){return date.getMonth();}
    public int getYear(){return date.getYear();}

    /**
     * override equals
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prietenie prietenie = (Prietenie) o;
        return ((idPrieten1 == prietenie.idPrieten1 && idPrieten2 == prietenie.idPrieten2) || (idPrieten1 == prietenie.idPrieten2 && idPrieten2 == prietenie.idPrieten1));
    }

    /**
     * override hashcode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, idPrieten1, idPrieten2);
    }

    /**
     * verifica daca un utilizator face parte din relatie de prietenie
     *
     * @param idUtlizator long
     * @return long - id-ul celuilalt prieten daca face parte din prietenie
     * -1 in caz contrar
     */
    public long prietenComun(long idUtlizator) {
        if (this.idPrieten1 == idUtlizator) return this.idPrieten2;
        if (this.idPrieten2 == idUtlizator) return this.idPrieten1;

        return -1;
    }

    /**
     * verifica daca utilizatorul face parte din prietenie
     *
     * @param idUtilizator long
     * @return boolean
     */
    public boolean memberOf(long idUtilizator) {

        return this.idPrieten1 == idUtilizator || idPrieten2 == idUtilizator;
    }

    public void setDate(LocalDateTime date) {
        this.date=date;
    }
}