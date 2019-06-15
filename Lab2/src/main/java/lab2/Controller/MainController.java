package lab2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/")
    public String getDefault() {
        return "redirect:login.mustache";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login.mustache";
    }
    @PostMapping("/login")
    public String postLoginPage() {
        return "login.mustache";
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