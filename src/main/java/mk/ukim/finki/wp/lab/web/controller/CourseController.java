package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        model.addAttribute("courses", courseService.listAll());
        return "listCourses";
    }

    @PostMapping("/add")
    public String addCourse(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam Long teacherId
    ) {
        this.courseService.addCourse(name, description, teacherId);
        return "redirect:/courses";
    }
    @GetMapping("/add")
    public String addCourseGet(Model model){
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers",teachers);
        return "add-course";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

}
