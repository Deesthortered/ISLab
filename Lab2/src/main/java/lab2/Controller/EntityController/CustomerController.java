package lab2.Controller.EntityController;

import lab2.Entity.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {
    @GetMapping("/Customer")
    @ResponseBody
    public String get(@RequestHeader("filter") String filter,
                      @RequestHeader("limited") String limited,
                      @RequestHeader("listBeginInd") String listBeginInd,
                      @RequestHeader("listSize") String listSize) {
        return "getCustomer";
    }

    @PostMapping("/Customer")
    @ResponseBody
    public String post(@RequestBody Customer entity) {
        return "postCustomer";
    }

    @PutMapping("/Customer")
    @ResponseBody
    public String put(@RequestBody Customer entity) {
        return "putCustomer";
    }

    @DeleteMapping("/Customer")
    @ResponseBody
    public String delete(@RequestBody String id) {
        return "deleteCustomer";
    }
}