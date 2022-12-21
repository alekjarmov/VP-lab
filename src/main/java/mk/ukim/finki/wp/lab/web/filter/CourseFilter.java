package mk.ukim.finki.wp.lab.web.filter;

import org.springframework.context.annotation.Profile;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebFilter
@Profile("no-security")
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
        excludedRoutes.put("/courses/delete/{id}", Arrays.asList("GET", "DELETE"));
        excludedRoutes.put("/courses/edit-form/{id}", Arrays.asList("GET"));
        excludedRoutes.put("/grades/list", Arrays.asList("GET", "POST"));
        excludedRoutes.put("/grades/format", Arrays.asList("GET", "POST"));
        String method = request.getMethod();
        // clean up the path will change the ids and so on
        path = cleanPath(excludedRoutes, path);

        boolean doRedirect = shouldRedirect(excludedRoutes, path, method);
        if (doRedirect && id == null) {
            response.sendRedirect("/courses");
        } else {
            filterChain.doFilter(request, response);
        }

    }

    public boolean shouldRedirect(Map<String, List<String>> excludedRoutes, String path, String method) {
        // if the path is not in the excluded routes then we should redirect
        if (excludedRoutes.containsKey(path)) {
            List<String> methods = excludedRoutes.get(path);
            return !methods.contains(method);
        }
        return true;
    }

    // replaces the link of a form /x/y/3432 with /x/y/{id}
    public String cleanPath(Map<String, List<String>> excludedRoutes, String path) {
        for (String key : excludedRoutes.keySet()) {
            String keyWithoutId = key.replaceFirst("/\\{id}","");

            if (path.contains(keyWithoutId) && key.contains("{id}")) {
                path = key;
                break;
            }
        }
        return path;
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
