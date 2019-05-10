package utility_package;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class Common {
    private static boolean initialized = false;

    public static final String str_true  = "true";
    public static final String str_false = "false";

    public static final String html_login = "/html/login_page.html";
    public static final String html_menu = "/html/menu.html";

    public static String url_basic;
    public static String url_login;
    public static String url_menu;

    public static final int session_max_age = 60*60;
    public static final int cookies_max_age = 60*60*24;

    public static final String atr_logged = "logged";
    public static final String atr_role   = "role";
    public static final String art_invalid_credentials = "invalid_credentials";

    public static final String par_logout          = "logout";
    public static final String q_get_role          = "get_role";

    public static final String q_entity_query    = "entity_query";
    public static final String q_get_entity_list = "get_entity_list";
    public static final String q_add_entity      = "add_entity";
    public static final String q_delete_entity   = "delete_entity";
    public static final String q_edit_entity     = "edit_entity";

    public static final String q_import = "import";
    public static final String q_export = "export";
    public static final String q_rebuild_database = "db_rebuild_database";
    public static final String q_rebuild_reports = "db_rebuild_reports";

    public static final String q_get_report_available = "get_report_available";
    public static final String q_import_report = "get_import_report_available";
    public static final String q_export_report = "get_export_report_available";

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