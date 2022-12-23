package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.TeacherFullNameConverter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = TeacherFullNameConverter.class)
    private TeacherFullName fullName;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfEmployment;

    public Teacher() {
    }

    public Teacher( String name, String surname) {
//        this.id = (long) (Math.random() * 1000);
        this.fullName = new TeacherFullName();
        this.fullName.setName(name);
        this.fullName.setSurname(surname);
        this.dateOfEmployment = LocalDate.now();
    }
    public Teacher( String name, String surname, LocalDate dateOfEmployment) {
        this.fullName = new TeacherFullName();
        this.fullName.setName(name);
        this.fullName.setSurname(surname);
        this.dateOfEmployment = dateOfEmployment;
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
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", fullName.toString(), id);
    }

    public String getName() {
        if (fullName == null)
            return null;
        return fullName.getName();
    }

    public String getSurname() {
        if (fullName == null)
            return null;
        return fullName.getSurname();
    }
}
