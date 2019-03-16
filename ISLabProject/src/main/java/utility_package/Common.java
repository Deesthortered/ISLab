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

    public static String jsp_login = "/jsp/login_page.jsp";
    public static String jsp_menu  = "/jsp/menu.jsp";

    public static String url_basic;
    public static String url_login;
    public static String url_menu;

    public static int session_max_age = 60*60;
    public static int cookies_max_age = 60*60*24;

    public static String atr_logged = "logged";
    public static String atr_role   = "role";
    public static String art_invalid_credentials = "invalid_credentials";

    public static String par_logout          = "logout";
    public static String q_get_role          = "get_role";

    public static String q_get_provider_list = "get_provider_list";
    public static String q_add_provider      = "add_provider";
    public static String q_delete_provider   = "delete_provider";
    public static String q_edit_provider     = "edit_provider";
    public static String q_get_one_provider  = "get_one_provider";

    public static String q_get_customer_list = "get_customer_list";
    public static String q_add_customer      = "add_customer";
    public static String q_delete_customer   = "delete_customer";
    public static String q_edit_customer     = "edit_customer";
    public static String q_get_one_customer  = "get_one_customer";

    public static String q_get_goods_list = "get_goods_list";
    public static String q_add_goods      = "add_goods";
    public static String q_delete_goods   = "delete_goods";
    public static String q_edit_goods     = "edit_goods";
    public static String q_get_one_goods  = "get_one_goods";

    public static String q_rebuild_base  = "rebuild_base";

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
