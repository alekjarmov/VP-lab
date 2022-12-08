package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentRepositoryInMemory {

    public List<Student> findAllStundents() {
        return DataHolder.students;
    }

    public List<Student> findAllByNameOrSurname(String text) {
        return DataHolder.students.stream()
                .filter(student -> student.getName()
                        .contains(text) || student.getSurname().contains(text))
                .collect(Collectors.toList());
    }

    public Student addStudent(Student student) {
        DataHolder.students.add(student);
        return student;
    }

    public Student findByUsername(String username) {
        return DataHolder.students.stream()
                .filter(student -> student.getUsername().equals(username))
                .findFirst().orElse(null);
    }

}
