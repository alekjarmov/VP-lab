package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {

    public List<Student> findAllStundents() {
        return DataHolder.students;
    }

    public List<Student> findAllByNameOrSurname(String text) {
        return DataHolder.students.stream().filter(student -> student.getName().contains(text) | student.getSurname().contains(text)).collect(Collectors.toList());
    }

}
