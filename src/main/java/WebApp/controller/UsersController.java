package WebApp.controller;

import WebApp.entity.User;
import WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserService userService;



    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody User user) {
        userService.insertUser(user);
        //userService.insertUser(user);
        String str = "Registration successful!";
        return ResponseEntity.ok(str);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long Id) {
        return userService.getUserById(Id);
    }


    @PutMapping("/refreshInfo")
    public ResponseEntity refreshInfo(@RequestBody User user) {
        userService.refreshUser(user);
        String str = "Your data was refreshing!";
        return ResponseEntity.ok(str);
    }

}
