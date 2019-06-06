package Utility;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public class Common {
    private static boolean initialized = false;
    public static String urlBasic;
    public static String urlLogin;
    public static String urlMenu;

    public static final String STR_TRUE = "true";
    public static final String STR_FALSE = "false";

    public static final String HTML_LOGIN = "/html/login_page.html";
    public static final String HTML_MENU = "/html/menu.html";

    public static final int SESSION_MAX_AGE = 60*60;
    public static final int COOKIES_MAX_AGE = 60*60*24;

    public static final String ATR_LOGGED = "logged";
    public static final String ATR_ROLE = "role";
    public static final String ART_INVALID_CREDENTIALS = "invalid_credentials";

    public static final String PAR_LOGOUT = "logout";

    public static boolean isInitialized() {
        return initialized;
    }
    public static void initialize(ServletRequest request) {
        initialized = true;

        urlBasic = ((HttpServletRequest) request).getContextPath() + "/";
        urlLogin = ((HttpServletRequest) request).getContextPath() + "/login";
        urlMenu = ((HttpServletRequest) request).getContextPath() + "/menu";
    }

    public static String readCookie(Cookie[] cookies, String key) {
        if (cookies == null)
            return null;
        String res =  String.valueOf(Arrays.stream(cookies)
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny());
        if (res.equals(Optional.empty().toString()))
            return null;
        return res.substring(9, res.length()-1);
    }
}