package lab2.Controller.EntityController;

import lab2.Entity.Customer;
import lab2.EntityQueryHandler.CustomerQueryHandler;
import lab2.EntityQueryHandler.EntityQueryHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class CustomerController {
    @GetMapping("/Customer")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) throws IOException {
        EntityQueryHandler handler = CustomerQueryHandler.getInstance();
        return handler.getEntityList(filter, limited, listBeginInd, listSize);
    }

    @PostMapping("/Customer")
    @ResponseBody
    public String post(@RequestBody Customer entity) {
        EntityQueryHandler handler = CustomerQueryHandler.getInstance();
        return handler.addEntity(entity);
    }

    @PutMapping("/Customer")
    @ResponseBody
    public String put(@RequestBody Customer entity) {
        EntityQueryHandler handler = CustomerQueryHandler.getInstance();
        return handler.editEntity(entity);
    }

    @DeleteMapping("/Customer")
    @ResponseBody
    public String delete(@RequestBody String id) {
        EntityQueryHandler handler = CustomerQueryHandler.getInstance();
        return handler.deleteEntity(id);
    }
}