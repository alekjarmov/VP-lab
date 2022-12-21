package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final CourseService courseService;
    private final StudentService studentService;
    public GradeServiceImpl(GradeRepository gradeRepository, @Lazy CourseService courseService,
                            @Lazy StudentService studentService) {
        this.gradeRepository = gradeRepository;
        this.courseService = courseService;
        this.studentService = studentService;
    }


    @Override
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    @Override
    public List<Grade> findAllByCourseId(Long courseId) {
        return gradeRepository.findAllByCourseCourseId(courseId);
    }

    @Override
    public Grade findByCourseIdAndStudentUsername(Long courseId, String username) {
        return gradeRepository.findByCourseCourseIdAndStudentUsername(courseId, username);
    }

    @Override
    public Grade save(Character grade, String username, Long courseId, LocalDateTime timestamp) {
        Student student = studentService.searchByUsername(username);
        Course course = courseService.findById(courseId);
        Grade g = new Grade(grade, student, course, timestamp);
        return gradeRepository.save(g);
    }

    @Override
    public Optional<Grade> findById(Long id) {
        return gradeRepository.findById(id);
    }

    @Override
    public List<Grade> findAllBetween(LocalDateTime from, LocalDateTime to) {
//        Long start = from.toEpochSecond(null);
//        Long end = to.toEpochSecond(null);
//        return gradeRepository.findAllByTimestampAfterAndTimestampBefore(start, end);
        return gradeRepository.findByTimestampBetween(from, to);
    }

    @Override
    public List<Grade> findByGradeBetween(Character from, Character to) {
        return gradeRepository.findByGradeBetween(from, to);
    }
}
