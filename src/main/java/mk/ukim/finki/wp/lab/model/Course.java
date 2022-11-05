package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import java.util.List;

@Data
public class Course {
    private Long courseId;
    private String name;
    private String description;
    private List<Student> students;
    private Teacher teacher;

    public Course() {
    }

    public Course(String name, String description, List<Student> students, Teacher teacher) {
        //generate a random id for course
        this.courseId = (long) (Math.random() * 1000000);
        this.name = name;
        this.description = description;
        this.students = students;
        this.teacher = teacher;
    }

}
