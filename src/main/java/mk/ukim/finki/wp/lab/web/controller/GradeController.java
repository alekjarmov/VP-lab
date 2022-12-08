package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/grades")
public class GradeController {
    private final GradeService gradeService;
    private final CourseService courseService;
    private final StudentService studentService;

    public GradeController(GradeService gradeService, CourseService courseService, StudentService studentService) {
        this.gradeService = gradeService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @GetMapping("/add")
    public String addGrade(@RequestParam Long courseId, @RequestParam String username, Model model){
        Course course = this.courseService.findById(courseId);
        model.addAttribute("course", course);
        Student student = this.studentService.searchByUsername(username);
        model.addAttribute("student", student);

        return "createGrade";
    }

    @PostMapping("/add")
    public String addGrade(@RequestParam Long courseId, @RequestParam String username, @RequestParam Character grade,
                           @RequestParam @DateTimeFormat (iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp){

        gradeService.save(grade, username, courseId, timestamp);
        return "redirect:/StudentEnrollmentSummary";
    }
    @GetMapping("/list")
    public String listGrades(Model model){
        model.addAttribute("grades", gradeService.findAll());
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        return "listGrades";
    }


}
