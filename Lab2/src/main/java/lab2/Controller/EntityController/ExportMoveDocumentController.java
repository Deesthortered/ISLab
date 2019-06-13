package lab2.Controller.EntityController;

import lab2.Entity.ExportMoveDocument;
import lab2.EntityQueryHandler.EntityQueryHandler;
import lab2.EntityQueryHandler.ExportMoveDocumentQueryHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ExportMoveDocumentController {
    @GetMapping("/ExportMoveDocument")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) throws IOException {
        EntityQueryHandler handler = ExportMoveDocumentQueryHandler.getInstance();
        return handler.getEntityList(filter, limited, listBeginInd, listSize);
    }

    @PostMapping("/ExportMoveDocument")
    @ResponseBody
    public String post(@RequestBody ExportMoveDocument entity) {
        EntityQueryHandler handler = ExportMoveDocumentQueryHandler.getInstance();
        return handler.addEntity(entity);
    }

    @PutMapping("/ExportMoveDocument")
    @ResponseBody
    public String put(@RequestBody ExportMoveDocument entity) {
        EntityQueryHandler handler = ExportMoveDocumentQueryHandler.getInstance();
        return handler.editEntity(entity);
    }

    @DeleteMapping("/ExportMoveDocument")
    @ResponseBody
    public String delete(@RequestBody String id) {
        EntityQueryHandler handler = ExportMoveDocumentQueryHandler.getInstance();
        return handler.deleteEntity(id);
    }
}