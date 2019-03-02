package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.entity.User;
import WebApp.service.OrganizatioService;
import WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    OrganizatioService organizatioService;

    @PostMapping
    public ResponseEntity registration(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable(value = "id") Long id){
        return userService.deleteUserById(id);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long Id) {
        return userService.getUserById(Id);
    }

    @PutMapping
    public ResponseEntity refreshInfo(@RequestBody User user) {
        return userService.refreshUser(user);
    }

    @PostMapping("/{id}")
    public ResponseEntity addOrganization(@PathVariable(value = "id") Long id, @RequestBody Organization organization ){
        return organizatioService.addOrganization(id,organization);
    }

}
