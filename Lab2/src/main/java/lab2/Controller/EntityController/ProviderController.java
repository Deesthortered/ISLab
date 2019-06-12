package lab2.Controller.EntityController;

import lab2.Entity.Provider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProviderController {
    @GetMapping("/Provider")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) {
        return "getProvider";
    }

    @PostMapping("/Provider")
    @ResponseBody
    public String post(@RequestBody Provider entity) {
        return "postProvider";
    }

    @PutMapping("/Provider")
    @ResponseBody
    public String put(@RequestBody Provider entity) {
        return "putProvider";
    }

    @DeleteMapping("/Provider")
    @ResponseBody
    public String delete(@RequestBody String id) {
        return "deleteProvider";
    }
}