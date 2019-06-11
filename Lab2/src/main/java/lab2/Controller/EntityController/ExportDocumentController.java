package lab2.Controller.EntityController;

import lab2.Model.ExportDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExportDocumentController {
    @GetMapping("/ExportDocument")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) {
        return "getExportDocument";
    }

    @PostMapping("/ExportDocument")
    @ResponseBody
    public String post(@RequestBody ExportDocument entity) {
        return "postExportDocument";
    }

    @PutMapping("/ExportDocument")
    @ResponseBody
    public String put(@RequestBody ExportDocument entity) {
        return "putExportDocument";
    }

    @DeleteMapping("/ExportDocument")
    @ResponseBody
    public String delete(@RequestBody String id) {
        return "deleteExportDocument";
    }
}