package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentEnrollmentSummaryServlet", urlPatterns = "/StudentEnrollmentSummary")
public class StudentEnrollmentSummaryServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final CourseService courseService;
    private final StudentService studentService;

    public StudentEnrollmentSummaryServlet(SpringTemplateEngine springTemplateEngine, CourseService courseService, StudentService studentService) {
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        long courseId = Long.parseLong(req.getSession().getAttribute("selectedCourse").toString());
        Course selectedCourse = courseService.findById(courseId);
        context.setVariable("selectedCourse", selectedCourse);
        List<Student> enrolledStudents = courseService.listStudentsByCourse(courseId);
        context.setVariable("enrolledStudents", enrolledStudents);
        springTemplateEngine.process("studentsInCourse.html", context, resp.getWriter());
    }
}
