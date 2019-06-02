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

    public static final String strTrue = "true";
    public static final String strFalse = "false";

    public static final String htmlLogin = "/html/login_page.html";
    public static final String html_menu = "/html/menu.html";

    public static final int session_max_age = 60*60;
    public static final int cookies_max_age = 60*60*24;

    public static final String atr_logged = "logged";
    public static final String atr_role   = "role";
    public static final String art_invalid_credentials = "invalid_credentials";

    public static final String par_logout        = "logout";
    public static final String q_get_role        = "get_role";

    public static final String q_entity_query    = "entity_query";
    public static final String q_get_entity_list = "get_entity_list";
    public static final String q_add_entity      = "add_entity";
    public static final String q_delete_entity   = "delete_entity";
    public static final String q_edit_entity     = "edit_entity";

    public static final String q_import          = "import";
    public static final String q_export          = "export";
    public static final String q_rebuild_reports = "db_rebuild_reports";

    public static boolean isInitialized() {
        return initialized;
    }
    public static void Initialize(ServletRequest request) {
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