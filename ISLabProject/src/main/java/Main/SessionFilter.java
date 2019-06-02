package Main;

import Utility.Common;

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
        if (logged == null || logged.equals(Common.strFalse)) {

            String cookie_logged = Common.readCookie(cookies, Common.atr_logged);
            if (cookie_logged != null && cookie_logged.equals(Common.strTrue)) {
                String cookie_role = Common.readCookie(cookies, Common.atr_role);
                if (cookie_role != null) {
                    session.setAttribute(Common.atr_logged, Common.strTrue);
                    session.setMaxInactiveInterval(Common.session_max_age);
                    session.setAttribute(Common.atr_role, cookie_role);
                    response.sendRedirect(Common.urlMenu);
                    return;
                }
            }

            if (url_current.equals(Common.urlMenu) || url_current.equals(Common.urlBasic))
                response.sendRedirect(Common.urlLogin);
            else
                chain.doFilter(req, resp);

        } else if (logged.equals(Common.strTrue)) {

            if (url_current.equals(Common.urlLogin) || url_current.equals(Common.urlBasic))
                response.sendRedirect(Common.urlMenu);
            else chain.doFilter(req, resp);
        } else {
            PrintWriter writer = response.getWriter();
            writer.println("ERROR 66");
        }
    }
}
