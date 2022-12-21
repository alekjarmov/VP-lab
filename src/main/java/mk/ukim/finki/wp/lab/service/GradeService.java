package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Grade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GradeService {
    public List<Grade> findAll();
    public List<Grade> findAllByCourseId(Long courseId);
    public Grade findByCourseIdAndStudentUsername(Long courseId, String username);
    public Grade save(Character grade, String username, Long courseId, LocalDateTime timestamp);
    public Optional<Grade> findById(Long id);
    public List<Grade> findAllBetween(LocalDateTime from, LocalDateTime to);
    public List<Grade> findByGradeBetween(Character from, Character to);

}
