package WebApp.controller;

import WebApp.entity.User;
import WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<Iterable<User>> greeeting() {
        return userService.getAllUsers();
    }

}
