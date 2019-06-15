package lab2.Controller.EntityController;

import lab2.EntityQueryHandler.EntityQueryHandler;
import lab2.EntityQueryHandler.ExportGoodsQueryHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ExportGoodsController {
    @GetMapping("/ExportGoods")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) throws IOException {
        EntityQueryHandler handler = ExportGoodsQueryHandler.getInstance();
        return handler.getEntityList(filter, limited, listBeginInd, listSize);
    }

    @PostMapping("/ExportGoods")
    @ResponseBody
    public String post(@RequestBody String entity) throws IOException {
        EntityQueryHandler handler = ExportGoodsQueryHandler.getInstance();
        return handler.addEntity(entity);
    }

    @PutMapping("/ExportGoods")
    @ResponseBody
    public String put(@RequestBody String entity) throws IOException {
        EntityQueryHandler handler = ExportGoodsQueryHandler.getInstance();
        return handler.editEntity(entity);
    }

    @DeleteMapping("/ExportGoods")
    @ResponseBody
    public String delete(@RequestBody String id) {
        EntityQueryHandler handler = ExportGoodsQueryHandler.getInstance();
        return handler.deleteEntity(id);
    }
}