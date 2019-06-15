package lab2.Controller.EntityController;

import lab2.Entity.AvailableGoods;
import lab2.EntityQueryHandler.AvailableGoodsQueryHandler;
import lab2.EntityQueryHandler.EntityQueryHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AvailableGoodsController {
    @GetMapping("/AvailableGoods")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) throws IOException {
        EntityQueryHandler handler = AvailableGoodsQueryHandler.getInstance();
        return handler.getEntityList(filter, limited, listBeginInd, listSize);
    }

    @PostMapping("/AvailableGoods")
    @ResponseBody
    public String post(@RequestBody String entity) throws IOException {
        EntityQueryHandler handler = AvailableGoodsQueryHandler.getInstance();
        return handler.addEntity(entity);
    }

    @PutMapping("/AvailableGoods")
    @ResponseBody
    public String put(@RequestBody String entity) throws IOException {
        EntityQueryHandler handler = AvailableGoodsQueryHandler.getInstance();
        return handler.editEntity(entity);
    }

    @DeleteMapping("/AvailableGoods")
    @ResponseBody
    public String delete(@RequestBody String id) {
        EntityQueryHandler handler = AvailableGoodsQueryHandler.getInstance();
        return handler.deleteEntity(id);
    }
}