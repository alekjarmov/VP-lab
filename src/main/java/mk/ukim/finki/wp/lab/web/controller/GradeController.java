package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
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
import java.util.List;

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

    @PostMapping("/filter")
    public String filterGrades(@RequestParam(required = false) @DateTimeFormat (iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                               @RequestParam(required = false) @DateTimeFormat (iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                               Model model){
        if (from == null || to == null){
            return "redirect:/grades/list";
        }
        List<Grade> filteredGrades = gradeService.findAllBetween(from, to);
        model.addAttribute("grades", filteredGrades);
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        return "listGrades";
    }
    @PostMapping("filterByGrade")
    public String filterByGrade(@RequestParam(required = false) Character grade1,
                                @RequestParam(required = false) Character grade2,
            Model model){
        if((grade1==null && grade2==null) || (grade1=='T' && grade2=='T')){
            return "redirect:/grades/list";
        }
        if (grade1 == null || grade1 == 'T'){
            grade1 = 'A';
        }
        if (grade2 == null || grade2 == 'T'){
            grade2 = 'F';
        }
        List<Grade> filteredGrades = gradeService.findByGradeBetween(grade1, grade2);
        model.addAttribute("grades", filteredGrades);
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        return "listGrades";
    }


}
