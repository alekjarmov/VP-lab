package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Character grade;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Course course;
    private LocalDateTime timestamp;

    public Grade() {
    }

    public Grade(Character grade, Student student, Course course, LocalDateTime timestamp) {
        this.grade = grade;
        this.student = student;
        this.course = course;
        this.timestamp = timestamp;
    }


}
