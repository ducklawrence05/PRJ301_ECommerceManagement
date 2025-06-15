/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filters;

import constants.Role;
import dtos.User;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    // Map ánh xạ URL pattern → role được phép
    private static final Map<String, Role[]> protectedUrls = new HashMap<>();

    // Khởi tạo ánh xạ phân quyền cho từng URL
    static {
        protectedUrls.put("/admin.jsp", new Role[]{Role.ADMIN});
        protectedUrls.put("/main/seller", new Role[]{Role.SELLER});
        protectedUrls.put("/main/accountant", new Role[]{Role.ACCOUNTANT});
        protectedUrls.put("/main/marketing", new Role[]{Role.MARKETING});
        protectedUrls.put("/main/delivery", new Role[]{Role.DELIVERY});
        protectedUrls.put("/main/support", new Role[]{Role.CUSTOMER_SUPPORT});
        protectedUrls.put("/main/buyer", new Role[]{Role.BUYER});
    }

    // Các trang không cần đăng nhập
    private static final Set<String> publicUrls = new HashSet<>(Arrays.asList(
        "/login.jsp",
        "/main/auth/login",
        "/main/auth/register",
        "/register.jsp",
        "/main/product",      // danh sách sản phẩm cho khách
        "/main/home"         // trang chủ public
    ));

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getServletPath();
        HttpSession session = request.getSession(false);

        // Bỏ qua trang public
        if (isPublic(path)) {
            chain.doFilter(req, res);
            return;
        }

        // Kiểm tra login
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("currentUser");
        Role userRole = user.getRole();

        // Kiểm tra quyền dựa vào URL
        if (isAuthorized(path, userRole)) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private boolean isPublic(String path) {
        for (String pub : publicUrls) {
            if (path.startsWith(pub)) return true;
        }
        return false;
    }

    private boolean isAuthorized(String path, Role userRole) {
        for (Map.Entry<String, Role[]> entry : protectedUrls.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                for (Role role : entry.getValue()) {
                    if (role == userRole) return true;
                }
                return false; // matched URL nhưng sai role
            }
        }
        return true; // không nằm trong vùng bảo vệ
    }
}