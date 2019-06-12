package lab2.Controller.EntityController;

import lab2.Entity.ImportDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImportDocumentController {
    @GetMapping("/ImportDocument")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) {
        return "getImportDocument";
    }

    @PostMapping("/ImportDocument")
    @ResponseBody
    public String post(@RequestBody ImportDocument entity) {
        return "postImportDocument";
    }

    @PutMapping("/ImportDocument")
    @ResponseBody
    public String put(@RequestBody ImportDocument entity) {
        return "putImportDocument";
    }

    @DeleteMapping("/ImportDocument")
    @ResponseBody
    public String delete(@RequestBody String id) {
        return "deleteImportDocument";
    }
}