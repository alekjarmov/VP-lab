package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.enumerations.Type;

import java.util.List;

@Data
public class Course implements Comparable<Course>{
    private Long courseId;
    private String name;
    private String description;
    private List<Student> students;
    private Teacher teacher;
    private Type type;

    public Course() {
    }

    public Course(String name, String description, List<Student> students, Teacher teacher) {
        //generate a random id for course
        this.courseId = (long) (Math.random() * 1000);
        this.name = name;
        this.description = description;
        this.students = students;
        this.teacher = teacher;
    }

    @Override
    public int compareTo(Course o) {
        return this.getName().compareTo(o.getName());
    }
}
