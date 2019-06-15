package lab2.Controller.EntityController;

import lab2.EntityQueryHandler.EntityQueryHandler;
import lab2.EntityQueryHandler.GoodsQueryHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class GoodsController {
    @GetMapping("/Goods")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) throws IOException {
        EntityQueryHandler handler = GoodsQueryHandler.getInstance();
        return handler.getEntityList(filter, limited, listBeginInd, listSize);
    }

    @PostMapping("/Goods")
    @ResponseBody
    public String post(@RequestBody String entity) throws IOException {
        EntityQueryHandler handler = GoodsQueryHandler.getInstance();
        return handler.addEntity(entity);
    }

    @PutMapping("/Goods")
    @ResponseBody
    public String put(@RequestBody String entity) throws IOException {
        EntityQueryHandler handler = GoodsQueryHandler.getInstance();
        return handler.editEntity(entity);
    }

    @DeleteMapping("/Goods")
    @ResponseBody
    public String delete(@RequestBody String id) {
        EntityQueryHandler handler = GoodsQueryHandler.getInstance();
        return handler.deleteEntity(id);
    }
}