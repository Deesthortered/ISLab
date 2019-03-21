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

    public static String str_true  = "true";
    public static String str_false = "false";

    public static String html_login = "/html/login_page.html";
    public static String html_menu = "/html/menu.html";

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

    public static String q_get_customer_list = "get_customer_list";
    public static String q_add_customer      = "add_customer";
    public static String q_delete_customer   = "delete_customer";
    public static String q_edit_customer     = "edit_customer";

    public static String q_get_goods_list = "get_goods_list";
    public static String q_add_goods      = "add_goods";
    public static String q_delete_goods   = "delete_goods";
    public static String q_edit_goods     = "edit_goods";

    public static String q_get_import_document_list = "get_import document_list";
    public static String q_add_import_document      = "add_import document";

    public static String q_get_export_document_list = "get_export document_list";
    public static String q_add_export_document      = "add_export document";

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

    public static String JavaDateToSQLDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return "" + year + "-" + month + "-" + day + "";
    }
    public static Date SQLDateToJavaDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date res = null;
        try {
            res = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }
}