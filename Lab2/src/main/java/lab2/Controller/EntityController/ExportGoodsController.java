package lab2.Controller.EntityController;

import lab2.Model.ExportGoods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExportGoodsController {
    @GetMapping("/ExportGoods")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) {
        return "getExportGoods";
    }

    @PostMapping("/ExportGoods")
    @ResponseBody
    public String post(@RequestBody ExportGoods entity) {
        return "postExportGoods";
    }

    @PutMapping("/ExportGoods")
    @ResponseBody
    public String put(@RequestBody ExportGoods entity) {
        return "putExportGoods";
    }

    @DeleteMapping("/ExportGoods")
    @ResponseBody
    public String delete(@RequestBody String id) {
        return "deleteExportGoods";
    }
}