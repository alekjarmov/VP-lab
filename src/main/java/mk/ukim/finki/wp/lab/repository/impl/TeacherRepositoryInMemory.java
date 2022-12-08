package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TeacherRepositoryInMemory {

    public List<Teacher> findAllTeachers() {
        return DataHolder.teachers;
    }

    public Optional<Teacher> findById(Long teacherId) {
        return DataHolder.teachers.stream().filter(teacher -> teacher.getId().equals(teacherId)).findFirst();
    }
    public int coureesTought(Teacher teacher ){
        return DataHolder.courses.stream().filter(course -> course.getTeacher().equals(teacher)).collect(Collectors.toList()).size();
    }
    public Optional<Teacher> bestTeacher(){
        return findAllTeachers().stream().max(Comparator.comparingInt(this::coureesTought));
    }

}
