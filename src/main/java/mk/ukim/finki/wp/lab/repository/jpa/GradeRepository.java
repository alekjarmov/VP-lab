package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    public List<Grade> findAllByCourseCourseId(Long courseId);
    public Grade findByCourseCourseIdAndStudentUsername(Long courseId, String username);
    public List<Grade> findAllByTimestampAfterAndTimestampBefore(Long start, Long end);
//    public List<Grade> findAllBetween(LocalDateTime from, LocalDateTime to);
    public List<Grade> findByTimestampBetween(LocalDateTime from, LocalDateTime to);
    public List<Grade> findByGradeBetween(Character from, Character to);
}

