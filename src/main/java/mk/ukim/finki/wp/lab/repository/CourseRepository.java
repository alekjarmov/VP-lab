package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Course saveCourse(String name, String description, Teacher teacher, Optional<Course> course) {
        //name desc students teacher
        // either editing or adding a new course
        return course.isPresent() ?
                editCourse(course.get(), name, description, teacher) :
                addCourse(name, description, teacher);
    }

    public Course editCourse(Course course, String name, String descripton, Teacher teacher) {
        course.setName(name);
        course.setDescription(descripton);
        course.setTeacher(teacher);
        return course;
    }

    public Course addCourse(String name, String description, Teacher teacher) {
        Course newCourse = new Course(name, description, new ArrayList<>(), teacher);
        DataHolder.courses.add(newCourse);
        return newCourse;
    }

    public Course deleteCourse(Long id) {
        Course toRemove = findById(id);
        DataHolder.courses.remove(toRemove);
        return toRemove;
    }
}
