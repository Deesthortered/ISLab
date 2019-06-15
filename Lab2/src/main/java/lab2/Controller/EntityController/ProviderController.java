package lab2.Controller.EntityController;

import lab2.EntityQueryHandler.EntityQueryHandler;
import lab2.EntityQueryHandler.ProviderQueryHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ProviderController {
    @GetMapping("/Provider")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) throws IOException {
        EntityQueryHandler handler = ProviderQueryHandler.getInstance();
        return handler.getEntityList(filter, limited, listBeginInd, listSize);
    }

    @PostMapping("/Provider")
    @ResponseBody
    public String post(@RequestBody String entity) throws IOException {
        EntityQueryHandler handler = ProviderQueryHandler.getInstance();
        return handler.addEntity(entity);
    }

    @PutMapping("/Provider")
    @ResponseBody
    public String put(@RequestBody String entity) throws IOException {
        EntityQueryHandler handler = ProviderQueryHandler.getInstance();
        return handler.editEntity(entity);
    }

    @DeleteMapping("/Provider")
    @ResponseBody
    public String delete(@RequestBody String id) {
        EntityQueryHandler handler = ProviderQueryHandler.getInstance();
        return handler.deleteEntity(id);
    }
}