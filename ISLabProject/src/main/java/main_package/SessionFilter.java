package main_package;

import utility_package.Common;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "SessionFilter")
public class SessionFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (!Common.isInitialized())
            Common.Initialize(req);

        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String url_current = request.getRequestURI();

        HttpSession session = request.getSession();
        String logged = (String) session.getAttribute(Common.atr_logged);

        Cookie[] cookies =  request.getCookies();
        if (logged == null || logged.equals(Common.str_false)) {

            String cookie_logged = Common.readCookie(cookies, Common.atr_logged);
            if (cookie_logged != null && cookie_logged.equals(Common.str_true)) {
                String cookie_role = Common.readCookie(cookies, Common.atr_role);
                if (cookie_role != null) {
                    session.setAttribute(Common.atr_logged, Common.str_true);
                    session.setMaxInactiveInterval(Common.session_max_age);
                    session.setAttribute(Common.atr_role, cookie_role);
                    response.sendRedirect(Common.url_menu);
                    return;
                }
            }

            if (url_current.equals(Common.url_login))
                chain.doFilter(req, resp);
            else  {
                response.sendRedirect(Common.url_login);
            }

        } else if (logged.equals(Common.str_true)) {

            if (url_current.equals(Common.url_login) || url_current.equals(Common.url_basic))
                response.sendRedirect(Common.url_menu);
            else if (url_current.substring(0, Common.url_menu.length()).equals(Common.url_menu))
                chain.doFilter(req, resp);
            else {
                PrintWriter writer = response.getWriter();
                writer.println("ERROR");
            }

        } else {
            PrintWriter writer = response.getWriter();
            writer.println("ERROR");
        }
    }
}
