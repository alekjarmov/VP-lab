package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Grade {
    private Long id;
    private Character grade;
    private Student student;
    private Course course;
    private LocalDateTime timestamp;

}
