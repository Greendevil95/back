package WebApp.controller;

import WebApp.entity.User;
import WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<Iterable<User>> greeeting() {
        return userService.getAllUser();
    }



// for test
    @PostMapping(value = "/{password}/{email}")
    public void addUser(@PathVariable("password") String password, @PathVariable("email") String email  ){
        userService.save(new User(password,email));
    }
}
