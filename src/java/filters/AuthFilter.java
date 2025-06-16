package filters;

import constants.Message;
import constants.Role;
import constants.Url;
import dtos.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/aaa"})
public class AuthFilter implements Filter {

    private static final Map<String, Role[]> protectedUrls = new HashMap<>();
    private static final Set<String> publicUrls = new HashSet<>(Arrays.asList(
            "/welcome.jsp",
            "/login.jsp",
            "/register.jsp",
            "/main/auth/login",
            "/main/auth/register",
            "/main/product" // được GET công khai
    ));

    static {
        protectedUrls.put("/admin.jsp", new Role[]{Role.ADMIN});
        protectedUrls.put("/main/user", new Role[]{Role.ADMIN});
        protectedUrls.put("/main/product", new Role[]{Role.ADMIN, Role.SELLER});
        protectedUrls.put("/main/accountant", new Role[]{Role.ACCOUNTANT});
        protectedUrls.put("/main/marketing", new Role[]{Role.MARKETING});
        protectedUrls.put("/main/delivery", new Role[]{Role.DELIVERY});
        protectedUrls.put("/main/support", new Role[]{Role.CUSTOMER_SUPPORT});
        protectedUrls.put("/main/product", new Role[]{Role.BUYER});
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String fullPath = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = fullPath.substring(contextPath.length());

        String method = request.getMethod();
        HttpSession session = request.getSession(false);

        // Bỏ qua file tĩnh
        if (path.startsWith("/css/") || path.endsWith(".css")) {
            chain.doFilter(req, res);
            return;
        }

        // Bỏ qua nếu là public URL
        if (isPublic(path, method)) {
            chain.doFilter(req, res);
            return;
        }

        // Kiểm tra login
        if (session == null || session.getAttribute("currentUser") == null) {
            request.setAttribute("MSG", Message.UNAUTHENTICATION);
            request.getRequestDispatcher(Url.LOGIN_PAGE).forward(req, res);
            return;
        }

        // Kiểm tra quyền
        User user = (User) session.getAttribute("currentUser");
        Role userRole = user.getRole();

        if (isAuthorized(path, method, userRole)) {
            chain.doFilter(req, res);
        } else {
            request.setAttribute("MSG", Message.UNAUTHORIZED);
            request.getRequestDispatcher(Url.ERROR_PAGE).forward(req, res);
        }
    }

    private boolean isPublic(String path, String method) {
        // GET /main/product thì public
        if (path.equals("/main/product") && method.equalsIgnoreCase("GET")) {
            return true;
        }

        if (path.equals("/") || path.isEmpty()) {
            return true;
        }

        for (String pub : publicUrls) {
            if (path.startsWith(pub)) {
                return true;
            }
        }

        return false;
    }

    private boolean isAuthorized(String path, String method, Role userRole) {
        for (Map.Entry<String, Role[]> entry : protectedUrls.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                for (Role role : entry.getValue()) {
                    if (role == userRole) {
                        return true;
                    }
                }
                return false; // matched URL nhưng sai role
            }
        }

        return true; // Không nằm trong vùng bảo vệ
    }
}
