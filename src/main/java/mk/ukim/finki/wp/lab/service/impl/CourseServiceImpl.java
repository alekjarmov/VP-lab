package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.CourseDoesNotExistException;
import mk.ukim.finki.wp.lab.model.exceptions.NoSuchUsernameException;
import mk.ukim.finki.wp.lab.model.exceptions.StudentAlreadyInCourseException;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentService studentService;

    public CourseServiceImpl(CourseRepository courseRepository, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return courseRepository.findAllStudentsByCourse(courseId);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Course course = courseRepository.findById(courseId);
        if (course == null) {
            throw new CourseDoesNotExistException();
        }
        Student student = studentService.searchByUsername(username);
        if (student == null) {
            throw new NoSuchUsernameException("No username selected");
        }
        if (course.getStudents().contains(student)) {
            throw new StudentAlreadyInCourseException();
        }
        return courseRepository.addStudentToCourse(student, course);
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAllCourses();
    }

    @Override
    public Course findById(Long courseId) {
        return courseRepository.findById(courseId);
    }
}
