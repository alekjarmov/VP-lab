package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.enumerations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Course implements Comparable<Course>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    @Column(columnDefinition="text")
    private String name;
    @Column(columnDefinition="text")
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Student> students;
    @ManyToOne
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grade> grades;


    public Course() {
    }

    public Course(String name, String description, List<Student> students, Teacher teacher) {
        //generate a random id for course
//        this.courseId = (long) (Math.random() * 1000);
        this.name = name;
        this.description = description;
        this.students = students;
        this.teacher = teacher;
        this.grades = new ArrayList<>();
    }
    public Course (String name, String description, Teacher teacher){
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.students = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public Course (String name, String description, Teacher teacher, Type type){
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.students = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.type = type;
    }

    @Override
    public int compareTo(Course o) {
        return this.getName().compareTo(o.getName());
    }
}
