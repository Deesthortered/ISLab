package lab2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DataController {

    @PostMapping("/import")
    public String makeImport(@RequestBody String body) {
        return "makeImport";
    }

    @PostMapping("/export")
    public String makeExport(@RequestBody String body) {
        return "makeExport";
    }

    @PostMapping("/rebuild")
    public String makeRebuild() {
        return "makeRebuild";
    }
}