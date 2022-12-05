package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class Teacher {
    private Long id;
    private String name;
    private String surname;
    private LocalDate dateOfEmployment;

    public Teacher() {
    }

    public Teacher( String name, String surname) {
        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getId(), teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname());
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d)", name, surname, id);
    }
}
