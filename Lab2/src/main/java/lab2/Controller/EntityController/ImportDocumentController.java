package lab2.Controller.EntityController;

import lab2.EntityQueryHandler.EntityQueryHandler;
import lab2.EntityQueryHandler.ImportDocumentQueryHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ImportDocumentController {
    @GetMapping("/ImportDocument")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) throws IOException {
        EntityQueryHandler handler = ImportDocumentQueryHandler.getInstance();
        return handler.getEntityList(filter, limited, listBeginInd, listSize);
    }

    @PostMapping("/ImportDocument")
    @ResponseBody
    public String post(@RequestBody String entity) throws IOException {
        EntityQueryHandler handler = ImportDocumentQueryHandler.getInstance();
        return handler.addEntity(entity);
    }

    @PutMapping("/ImportDocument")
    @ResponseBody
    public String put(@RequestBody String entity) throws IOException {
        EntityQueryHandler handler = ImportDocumentQueryHandler.getInstance();
        return handler.editEntity(entity);
    }

    @DeleteMapping("/ImportDocument")
    @ResponseBody
    public String delete(@RequestBody String id) {
        EntityQueryHandler handler = ImportDocumentQueryHandler.getInstance();
        return handler.deleteEntity(id);
    }
}