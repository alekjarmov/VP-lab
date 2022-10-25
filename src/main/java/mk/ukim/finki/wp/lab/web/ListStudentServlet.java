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

@WebServlet(name = "ListStudentServlet", urlPatterns = "/AddStudent")
public class ListStudentServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final CourseService courseService;
    private final StudentService studentService;

    public ListStudentServlet(SpringTemplateEngine springTemplateEngine, CourseService courseService, StudentService studentService) {
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("students", studentService.listAll());
        String selectedCourse = req.getSession().getAttribute("selectedCourse").toString();
        // check to see if any error was forwarded when selecting the student
        if (req.getSession().getAttribute("hasError") != null) {
            context.setVariable("hasError", true);
            context.setVariable("error", req.getSession().getAttribute("error"));
            req.getSession().removeAttribute("hasError");
            req.getSession().removeAttribute("error");
        }
        context.setVariable("selectedCourse", selectedCourse);
        springTemplateEngine.process("listStudents.html", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("selectedCourse",req.getParameter("courseId"));
        resp.sendRedirect("/AddStudent");
    }
}
