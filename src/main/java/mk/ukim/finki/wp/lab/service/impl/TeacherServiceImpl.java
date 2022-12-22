package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final CourseService courseService;
    public TeacherServiceImpl(TeacherRepository teacherRepository, @Lazy CourseService courseService) {
        this.teacherRepository = teacherRepository;
        this.courseService = courseService;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Optional<Teacher> bestTeacher() {
        return findAll().stream().max(Comparator.comparingInt(this::coursesTought));
    }

    @Override
    public int coursesTought(Teacher teacher) {
        return courseService.listAll().stream().filter(x-> x.getTeacher().equals(teacher)).collect(Collectors.toList()).size();
    }

    @Override
    public Teacher save(String name, String surname, LocalDate timeOfEmployment) {
        if (timeOfEmployment==null){
            return teacherRepository.save(new Teacher(name,surname));
        }
        return teacherRepository.save(new Teacher(name, surname,timeOfEmployment));
    }
}
