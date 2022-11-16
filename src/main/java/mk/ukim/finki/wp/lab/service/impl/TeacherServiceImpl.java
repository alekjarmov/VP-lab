package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.TeacherRepository;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAllTeachers();
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return DataHolder.teachers.stream().filter(x-> x.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<Teacher> bestTeacher() {
        return teacherRepository.bestTeacher();
    }

    @Override
    public int coursesTought(Teacher teacher) {
        return teacherRepository.coureesTought(teacher);
    }
}
