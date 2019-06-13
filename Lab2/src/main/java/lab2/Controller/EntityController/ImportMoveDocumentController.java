package lab2.Controller.EntityController;

import lab2.Entity.ImportMoveDocument;
import lab2.EntityQueryHandler.EntityQueryHandler;
import lab2.EntityQueryHandler.ImportMoveDocumentQueryHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ImportMoveDocumentController {
    @GetMapping("/ImportMoveDocument")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) throws IOException {
        EntityQueryHandler handler = ImportMoveDocumentQueryHandler.getInstance();
        return handler.getEntityList(filter, limited, listBeginInd, listSize);
    }

    @PostMapping("/ImportMoveDocument")
    @ResponseBody
    public String post(@RequestBody ImportMoveDocument entity) {
        EntityQueryHandler handler = ImportMoveDocumentQueryHandler.getInstance();
        return handler.addEntity(entity);
    }

    @PutMapping("/ImportMoveDocument")
    @ResponseBody
    public String put(@RequestBody ImportMoveDocument entity) {
        EntityQueryHandler handler = ImportMoveDocumentQueryHandler.getInstance();
        return handler.editEntity(entity);
    }

    @DeleteMapping("/ImportMoveDocument")
    @ResponseBody
    public String delete(@RequestBody String id) {
        EntityQueryHandler handler = ImportMoveDocumentQueryHandler.getInstance();
        return handler.deleteEntity(id);
    }
}