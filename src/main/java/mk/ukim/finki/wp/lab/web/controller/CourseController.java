package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Course> courses = courseService.listAll().stream().sorted().collect(Collectors.toList());
        model.addAttribute("courses", courses);
        Optional<Teacher> teacher = teacherService.bestTeacher();
        model.addAttribute("bestTeacher", teacher.orElse(null));
        if (teacher.isPresent()){
            model.addAttribute("bestTeacherCourses", teacherService.coursesTought(teacher.get()));
        }else{
            model.addAttribute("bestTeacherCourses", 0);
        }
        return "listCourses";
    }

    @PostMapping("/add")
    public String addCourse(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam Long teacherId,
                            @RequestParam(required = false) Long courseId,
                            HttpServletRequest request
    ) {
        Optional<Long> optionalCourseId = Optional.ofNullable(courseId);
        try{
            this.courseService.saveCourse(name, description, teacherId, optionalCourseId);
        }catch (RuntimeException e){
            return String.format("redirect:/courses?error=%s", e.getMessage());
        }
        request.getSession().invalidate();
        return "redirect:/courses";
    }

    @GetMapping("/add")
    public String addCourseGet(Model model) {
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "add-course";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if(courseService.findById(id) != null){
            Course course = courseService.findById(id);
            model.addAttribute("course", course);
            List<Teacher> teachers = teacherService.findAll();
            model.addAttribute("teachers", teachers);
            return "add-course";
        }
        return "redirect:/courses";
    }

}
