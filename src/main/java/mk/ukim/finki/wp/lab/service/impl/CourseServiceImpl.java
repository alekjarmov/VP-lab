package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumerations.Type;
import mk.ukim.finki.wp.lab.model.exceptions.CourseDoesNotExistException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidFormParameters;
import mk.ukim.finki.wp.lab.model.exceptions.NoSuchUsernameException;
import mk.ukim.finki.wp.lab.model.exceptions.StudentAlreadyInCourseException;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return courseRepository.findById(courseId).orElseThrow(CourseDoesNotExistException::new).getStudents();
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        if (!courseRepository.findById(courseId).isPresent())
            throw new CourseDoesNotExistException();
        Course course = courseRepository.findById(courseId).get();
        Student student = studentService.searchByUsername(username);
        if (student == null) {
            throw new NoSuchUsernameException("No username selected");
        }
        if (course.getStudents().contains(student)) {
            throw new StudentAlreadyInCourseException();
        }
        course.getStudents().add(student);
        return courseRepository.save(course);
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long courseId) {
        // better for exception to be thrown
        return courseRepository.findById(courseId).orElse(null);
    }

    @Override
    @Transactional
    public Course saveCourse(String name, String description, Long teacherId, Optional<Long> courseId, Type type) {
        Teacher teacherForCourse = teacherService.findById(teacherId).orElse(null); // should be exception
        Course course = null;
        boolean nameTaken = listAll().stream().anyMatch(x -> x.getName().equals(name));
        if (courseId.isPresent()) { //editing here
            course = findById(courseId.get());
            if (!course.getName().equals(name) && nameTaken) {
                throw new InvalidFormParameters(String.format("A course with the name '%s' already exists!", name));
            }
            course.setName(name);
            course.setDescription(description);
            course.setTeacher(teacherForCourse);
            course.setType(type);
        } else { // adding here
            if (nameTaken) {
                throw new InvalidFormParameters(String.format("A course with the name '%s' already exists!", name));
            }
            course = new Course(name, description, teacherForCourse, type);
        }
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
