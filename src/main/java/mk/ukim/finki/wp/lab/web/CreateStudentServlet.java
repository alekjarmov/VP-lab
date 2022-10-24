package mk.ukim.finki.wp.lab.web;

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

@WebServlet(name = "CreateStudentsServlet", urlPatterns = "/CreateStudent")
public class CreateStudentServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final CourseService courseService;
    private final StudentService studentService;

    public CreateStudentServlet(SpringTemplateEngine springTemplateEngine, CourseService courseService, StudentService studentService) {
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WebContext context = new WebContext(req, resp, req.getServletContext());

        springTemplateEngine.process("createStudent.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        if(req.getParameterMap().values().stream().anyMatch(value -> null == value || value.length == 0)){
            context.setVariable("error", "All fields are required");
            springTemplateEngine.process("createStudent.html", context, resp.getWriter());
        }
        studentService.save(username, password, name, surname);
        resp.sendRedirect("/AddStudent");

    }
}
