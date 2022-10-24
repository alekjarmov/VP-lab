package mk.ukim.finki.wp.lab.bootstrap;

import lombok.Getter;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component // ova znaci deka e singleton
@Getter
public class DataHolder {
    public static List<Student> students = new ArrayList<>();
    public static List<Course> courses = new ArrayList<>();

    @PostConstruct // this is executed after instancing the class
    public void init() {
        for (int i = 0; i < 5; i++) {
            String name = String.format("Name %d", i);
            String surname = String.format("Surname %d", i);
            String username = String.format("user%d", i);
            String password = String.format("pw%d%d", i, i + 1);
            students.add(new Student(username, password, name, surname));
        }
        for (int i = 0; i < 5; i++) {
            final Long id = (long) i; // can not cast to (Long) other choies are Long.valueOf(i) or new Long(i)
            String name = String.format("Course %d", i);
            String description = String.format("Description for course %d", i);
            List<Student> studentList = students.stream().filter(student -> !student.getName().equals(String.format("Name %d", id))).collect(Collectors.toList());
            courses.add(new Course(id, name, description, studentList));
        }
    }
}
