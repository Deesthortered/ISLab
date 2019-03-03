package utility_package;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public class Common {
    private static boolean initialized = false;

    public static String str_true  = "true";
    public static String str_false = "false";

    public static String url_basic;
    public static String url_login;
    public static String url_menu;

    public static String atr_logged = "logged";
    public static String atr_role   = "role";

    public static String html_login                 = "/html/login_page.html";
    public static String html_menu_admin            = "/html/admin_menu.html";
    public static String html_menu_view_manager     = "/html/view_manager_menu.html";
    public static String html_menu_import_manager   = "/html/import_manager_menu.html";
    public static String html_menu_export_manager   = "/html/export_manager_menu.html";

    public static int session_max_age = 60;
    public static int cookies_max_age = 60*60*24;

    public static boolean isInitialized() {
        return initialized;
    }
    public static void Initialize(ServletRequest request) {
        initialized = true;

        url_basic = ((HttpServletRequest) request).getContextPath() + "/";
        url_login = ((HttpServletRequest) request).getContextPath() + "/login";
        url_menu  = ((HttpServletRequest) request).getContextPath() + "/menu";

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