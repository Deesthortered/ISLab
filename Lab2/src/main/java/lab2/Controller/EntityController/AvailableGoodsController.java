package lab2.Controller.EntityController;

import lab2.Model.AvailableGoods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AvailableGoodsController {
    @GetMapping("/AvailableGoods")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) {
        return "getAvailableGoods";
    }

    @PostMapping("/AvailableGoods")
    @ResponseBody
    public String post(@RequestBody AvailableGoods entity) {
        return "postAvailableGoods";
    }

    @PutMapping("/AvailableGoods")
    @ResponseBody
    public String put(@RequestBody AvailableGoods entity) {
        return "putAvailableGoods";
    }

    @DeleteMapping("/AvailableGoods")
    @ResponseBody
    public String delete(@RequestBody String id) {
        return "deleteAvailableGoods";
    }
}