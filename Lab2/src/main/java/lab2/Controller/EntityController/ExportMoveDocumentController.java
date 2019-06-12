package lab2.Controller.EntityController;

import lab2.Entity.ExportMoveDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExportMoveDocumentController {
    @GetMapping("/ExportMoveDocument")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) {
        return "getExportMoveDocument";
    }

    @PostMapping("/ExportMoveDocument")
    @ResponseBody
    public String post(@RequestBody ExportMoveDocument entity) {
        return "postExportMoveDocument";
    }

    @PutMapping("/ExportMoveDocument")
    @ResponseBody
    public String put(@RequestBody ExportMoveDocument entity) {
        return "putExportMoveDocument";
    }

    @DeleteMapping("/ExportMoveDocument")
    @ResponseBody
    public String delete(@RequestBody String id) {
        return "deleteExportMoveDocument";
    }
}