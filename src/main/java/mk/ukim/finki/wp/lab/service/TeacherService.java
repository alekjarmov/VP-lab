package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TeacherService {
    public List<Teacher> findAll();
    public Optional<Teacher> findById(Long id);
    public Optional<Teacher> bestTeacher();
    public int coursesTought(Teacher teacher);
    public Teacher save(String name, String surname, LocalDate timeOfEmployment);
}
