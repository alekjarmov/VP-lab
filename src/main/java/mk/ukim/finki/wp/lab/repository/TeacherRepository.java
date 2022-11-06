package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TeacherRepository {

    public List<Teacher> findAllTeachers() {
        return DataHolder.teachers;
    }

    public Optional<Teacher> findById(Long teacherId) {
        return DataHolder.teachers.stream().filter(teacher -> teacher.getId().equals(teacherId)).findFirst();
    }

}
