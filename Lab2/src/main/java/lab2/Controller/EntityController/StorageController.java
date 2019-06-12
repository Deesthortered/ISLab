package lab2.Controller.EntityController;

import lab2.Entity.Storage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class StorageController {
    @GetMapping("/Storage")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) {
        return "getStorage";
    }

    @PostMapping("/Storage")
    @ResponseBody
    public String post(@RequestBody Storage entity) {
        return "postStorage";
    }

    @PutMapping("/Storage")
    @ResponseBody
    public String put(@RequestBody Storage entity) {
        return "putStorage";
    }

    @DeleteMapping("/Storage")
    @ResponseBody
    public String delete(@RequestBody String id) {
        return "deleteStorage";
    }
}