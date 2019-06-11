package lab2.Controller.EntityController;

import lab2.Model.ImportMoveDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImportMoveDocumentController {
    @GetMapping("/ImportMoveDocument")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) {
        return "getImportMoveDocument";
    }

    @PostMapping("/ImportMoveDocument")
    @ResponseBody
    public String post(@RequestBody ImportMoveDocument entity) {
        return "postImportMoveDocument";
    }

    @PutMapping("/ImportMoveDocument")
    @ResponseBody
    public String put(@RequestBody ImportMoveDocument entity) {
        return "putImportMoveDocument";
    }

    @DeleteMapping("/ImportMoveDocument")
    @ResponseBody
    public String delete(@RequestBody String id) {
        return "deleteImportMoveDocument";
    }
}