package lab2.Controller.EntityController;

import lab2.Entity.ImportGoods;
import lab2.EntityQueryHandler.EntityQueryHandler;
import lab2.EntityQueryHandler.ImportGoodsQueryHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ImportGoodsController {
    @GetMapping("/ImportGoods")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) throws IOException {
        EntityQueryHandler handler = ImportGoodsQueryHandler.getInstance();
        return handler.getEntityList(filter, limited, listBeginInd, listSize);
    }

    @PostMapping("/ImportGoods")
    @ResponseBody
    public String post(@RequestBody ImportGoods entity) {
        EntityQueryHandler handler = ImportGoodsQueryHandler.getInstance();
        return handler.addEntity(entity);
    }

    @PutMapping("/ImportGoods")
    @ResponseBody
    public String put(@RequestBody ImportGoods entity) {
        EntityQueryHandler handler = ImportGoodsQueryHandler.getInstance();
        return handler.editEntity(entity);
    }

    @DeleteMapping("/ImportGoods")
    @ResponseBody
    public String delete(@RequestBody String id) {
        EntityQueryHandler handler = ImportGoodsQueryHandler.getInstance();
        return handler.deleteEntity(id);
    }
}