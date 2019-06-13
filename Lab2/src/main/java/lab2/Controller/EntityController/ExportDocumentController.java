package lab2.Controller.EntityController;

import lab2.Entity.ExportDocument;
import lab2.EntityQueryHandler.EntityQueryHandler;
import lab2.EntityQueryHandler.ExportDocumentQueryHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ExportDocumentController {
    @GetMapping("/ExportDocument")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) throws IOException {
        EntityQueryHandler handler = ExportDocumentQueryHandler.getInstance();
        return handler.getEntityList(filter, limited, listBeginInd, listSize);
    }

    @PostMapping("/ExportDocument")
    @ResponseBody
    public String post(@RequestBody ExportDocument entity) {
        EntityQueryHandler handler = ExportDocumentQueryHandler.getInstance();
        return handler.addEntity(entity);
    }

    @PutMapping("/ExportDocument")
    @ResponseBody
    public String put(@RequestBody ExportDocument entity) {
        EntityQueryHandler handler = ExportDocumentQueryHandler.getInstance();
        return handler.editEntity(entity);
    }

    @DeleteMapping("/ExportDocument")
    @ResponseBody
    public String delete(@RequestBody String id) {
        EntityQueryHandler handler = ExportDocumentQueryHandler.getInstance();
        return handler.deleteEntity(id);
    }
}