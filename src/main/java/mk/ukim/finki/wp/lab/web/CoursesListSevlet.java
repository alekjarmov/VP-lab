package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "ListCoursesServlet", urlPatterns = "/listCourses")
public class CoursesListSevlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final CourseService courseService;
    private final TeacherService teacherService;

    public CoursesListSevlet(SpringTemplateEngine springTemplateEngine, CourseService courseService, TeacherService teacherService) {
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("courses", courseService.listAll());
        List<Teacher> teachers = teacherService.findAll();
        context.setVariable("teachers", teachers);
        Optional<Teacher> teacher = teacherService.bestTeacher();
        context.setVariable("bestTeacher", teacher.orElse(null));
        if (teacher.isPresent()){
            context.setVariable("bestTeacherCourses", teacherService.coursesTought(teacher.get()));
        }else{
            context.setVariable("bestTeacherCourses", 0);
        }
        resp.setContentType("text/html; charset=UTF-8");
        springTemplateEngine.process("listCourses.html", context, resp.getWriter());

    }

}
