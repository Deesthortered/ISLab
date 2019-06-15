package lab2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login_page";
    }
    @PostMapping("/login")
    public String postLoginPage() {
        return "login_page";
    }

    @GetMapping("/menu") // Логаут или задать сессию/куки
    public String getMenu() {
        return "menu";
    }
    @PostMapping("/menu") // Возвращает роль
    @ResponseBody
    public String postMenu() {
        return "Admin";
    }
}