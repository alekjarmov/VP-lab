package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;
import java.util.Optional;

public interface CourseService{
    List<Student> listStudentsByCourse(Long courseId);
    Course addStudentInCourse(String username, Long courseId);
    List<Course> listAll();
    Course findById(Long courseId);
    Course saveCourse(String name, String description, Long teacherId, Optional<Long> courseId);
    Course deleteCourse(Long id);
}
