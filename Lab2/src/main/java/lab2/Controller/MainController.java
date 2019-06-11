package lab2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/menu")
    public String getMenu() {
        return "menu";
    }
    @PostMapping("/menu")
    public String postMenu() {
        return "menu";
    }
}