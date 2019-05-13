package WebApp.controller;

import WebApp.service.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/other")
public class OtherController {
    @GetMapping("/category")
    public ResponseEntity getAllCategory() {
        return AbstractService.getAllCategory();
    }
}
