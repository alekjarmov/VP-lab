package mk.ukim.finki.wp.lab.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebFilter
public class CourseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String id = (String) request.getSession().getAttribute("selectedCourse");
        String path = request.getServletPath();

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        List<String> goodPaths = new ArrayList<>();

        Map<String, List<String>> excludedRoutes = new HashMap<>();
        excludedRoutes.put("/courses", Arrays.asList("GET"));
        excludedRoutes.put("/AddStudent", Arrays.asList("POST"));
        excludedRoutes.put("/courses/add", Arrays.asList("POST", "GET"));

        String method = request.getMethod();
        boolean doRedirect = shouldRedirect(excludedRoutes, path, method);
        if (doRedirect && id == null) {
            response.sendRedirect("/courses");
        } else {
            filterChain.doFilter(request, response);
        }

    }

    public boolean shouldRedirect(Map<String, List<String>> excludedRoutes, String path, String method) {
        if (excludedRoutes.containsKey(path)) {
            List<String> methods = excludedRoutes.get(path);
            return !methods.contains(method);
        }
        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
