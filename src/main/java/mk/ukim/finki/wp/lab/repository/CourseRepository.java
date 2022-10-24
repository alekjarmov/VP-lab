package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepository {
    public List<Course> findAllCourses() {
        return DataHolder.courses;
    }

    public Course findById(Long courseId) {
        return DataHolder.courses.stream()
                .filter(course -> course.getCourseId()
                        .equals(courseId)).findFirst().orElse(null);
    }

    public List<Student> findAllStudentsByCourse(Long courseId) {
        return findById(courseId).getStudents();
    }

    public Course addStudentToCourse(Student student, Course course) {
//         Se nadevam nema potreba od dolnovo
//        int ind = DataHolder.courses.indexOf(course);
//        DataHolder.courses.get(ind).getStudents().add(student);

        course.getStudents().add(student);
        return course;
    }



}
