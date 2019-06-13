package lab2.Controller.EntityController;

import lab2.Entity.Storage;
import lab2.EntityQueryHandler.EntityQueryHandler;
import lab2.EntityQueryHandler.StorageQueryHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class StorageController {
    @GetMapping("/Storage")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) throws IOException {
        EntityQueryHandler handler = StorageQueryHandler.getInstance();
        return handler.getEntityList(filter, limited, listBeginInd, listSize);
    }

    @PostMapping("/Storage")
    @ResponseBody
    public String post(@RequestBody Storage entity) {
        EntityQueryHandler handler = StorageQueryHandler.getInstance();
        return handler.addEntity(entity);
    }

    @PutMapping("/Storage")
    @ResponseBody
    public String put(@RequestBody Storage entity) {
        EntityQueryHandler handler = StorageQueryHandler.getInstance();
        return handler.editEntity(entity);
    }

    @DeleteMapping("/Storage")
    @ResponseBody
    public String delete(@RequestBody String id) {
        EntityQueryHandler handler = StorageQueryHandler.getInstance();
        return handler.deleteEntity(id);
    }
}