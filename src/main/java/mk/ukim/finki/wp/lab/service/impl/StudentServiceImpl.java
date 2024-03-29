package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidFormParameters;
import mk.ukim.finki.wp.lab.model.exceptions.UsernameTakenException;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAll() {

        return studentRepository.findAll();
    }

    @Override
    public List<Student> searchByNameOrSurname(String text) {
        return studentRepository.findByNameLikeOrSurnameLike(text, text);
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || name == null || name.isEmpty() || surname == null || surname.isEmpty())
            throw new InvalidFormParameters();
        if (searchByUsername(username) != null)
            throw new UsernameTakenException();
        Student student = new Student(username, password, name, surname);
        return studentRepository.save(student);
    }

    @Override
    public Student searchByUsername(String username) {
        return studentRepository.findById(username).orElse(null);
    }

}
