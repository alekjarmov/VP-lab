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
        if (req.getParameter("newCourse")!= null){
            String courseId = req.getParameter("newCourse");
            req.getSession().setAttribute("selectedCourse",courseId);
        }
        long courseId = Long.parseLong(req.getSession().getAttribute("selectedCourse").toString());
        Course selectedCourse = courseService.findById(courseId);
        context.setVariable("course", selectedCourse);
        List<Student> enrolledStudents = courseService.listStudentsByCourse(courseId);
        context.setVariable("enrolledStudents", enrolledStudents);
        // req.getSession().removeAttribute("selectedCourse"); // se trga vekje izbraniot kurs
        context.setVariable("courses", courseService.listAll());
        springTemplateEngine.process("studentsInCourse.html", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        long courseId = Long.parseLong(req.getSession().getAttribute("selectedCourse").toString());
        String username  = req.getParameter("size");
        try {
            courseService.addStudentInCourse(username, courseId);
            resp.sendRedirect("/StudentEnrollmentSummary");
        } catch (RuntimeException ex){
            req.getSession().setAttribute("hasError", true);
            req.getSession().setAttribute("error", ex.getMessage());
            resp.sendRedirect("/AddStudent");
        }
        // resp.sendRedirect can not be outside because it will try to execute after the foward
    }
}
