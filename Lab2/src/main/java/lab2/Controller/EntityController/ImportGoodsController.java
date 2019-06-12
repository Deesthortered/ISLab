package lab2.Controller.EntityController;

import lab2.Entity.ImportGoods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImportGoodsController {
    @GetMapping("/ImportGoods")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) {
        return "getImportGoods";
    }

    @PostMapping("/ImportGoods")
    @ResponseBody
    public String post(@RequestBody ImportGoods entity) {
        return "postImportGoods";
    }

    @PutMapping("/ImportGoods")
    @ResponseBody
    public String put(@RequestBody ImportGoods entity) {
        return "putImportGoods";
    }

    @DeleteMapping("/ImportGoods")
    @ResponseBody
    public String delete(@RequestBody String id) {
        return "deleteImportGoods";
    }
}