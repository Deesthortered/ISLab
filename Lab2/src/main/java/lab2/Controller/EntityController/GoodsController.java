package lab2.Controller.EntityController;

import lab2.Entity.Goods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GoodsController {
    @GetMapping("/Goods")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) {
        return "getGoods";
    }

    @PostMapping("/Goods")
    @ResponseBody
    public String post(@RequestBody Goods entity) {
        return "postGoods";
    }

    @PutMapping("/Goods")
    @ResponseBody
    public String put(@RequestBody Goods entity) {
        return "putGoods";
    }

    @DeleteMapping("/Goods")
    @ResponseBody
    public String delete(@RequestBody String id) {
        return "deleteGoods";
    }
}