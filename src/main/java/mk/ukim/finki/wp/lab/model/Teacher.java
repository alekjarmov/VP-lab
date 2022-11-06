package mk.ukim.finki.wp.lab.model;

import lombok.Data;

@Data
public class Teacher {
    private Long id;
    private String name;
    private String surname;

    public Teacher() {
    }

    public Teacher( String name, String surname) {
        this.id = (long) (Math.random() * 1000000);
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d)", name, surname, id);
    }
}
