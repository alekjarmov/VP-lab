package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.exceptions.CourseDoesNotExistException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidFormParameters;
import mk.ukim.finki.wp.lab.model.exceptions.NoSuchUsernameException;
import mk.ukim.finki.wp.lab.model.exceptions.StudentAlreadyInCourseException;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public CourseServiceImpl(CourseRepository courseRepository, StudentService studentService, TeacherService teacherService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
        this.teacherService = teacherService;
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

    @Override
    public Course saveCourse(String name, String description, Long teacherId, Optional<Long> courseId) {
        Teacher teacherForCourse = teacherService.findById(teacherId).orElse(null); // should be exception
        Optional<Course> course = Optional.empty();
        boolean nameTaken = listAll().stream().anyMatch(x -> x.getName().equals(name));
        if (courseId.isPresent()) {
            course = Optional.of(findById(courseId.get()));
            if (!course.get().getName().equals(name)) {
                throw new InvalidFormParameters(String.format("A course with the name '%s' already exists!", name));
            }
        } else {
            if (nameTaken) {
                throw new InvalidFormParameters(String.format("A course with the name '%s' already exists!", name));
            }
        }
        return courseRepository.saveCourse(name, description, teacherForCourse, course);
    }

    @Override
    public Course deleteCourse(Long id) {
        return courseRepository.deleteCourse(id);
    }
}
