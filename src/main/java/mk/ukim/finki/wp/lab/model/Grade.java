package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    public Grade() {
    }

    public Grade(Character grade, Student student, Course course, LocalDateTime timestamp) {
        this.grade = grade;
        this.student = student;
        this.course = course;
        this.timestamp = timestamp;
    }
    public Grade(Character grade, Student student, Course course) {
        this.grade = grade;
        this.student = student;
        this.course = course;
        this.timestamp = LocalDateTime.now();
    }

    public Character getGrade(){
        return this.grade;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
