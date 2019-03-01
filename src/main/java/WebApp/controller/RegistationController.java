package WebApp.controller;

import WebApp.entity.User;
import WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class RegistationController {

    @Autowired
    private UserService userService;



    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long Id) {
        return userService.getUserById(Id);
    }

    @PutMapping("/refreshInfo")
    public ResponseEntity refreshInfo(@RequestBody User user) {
        return userService.refreshUser(user);
    }

}