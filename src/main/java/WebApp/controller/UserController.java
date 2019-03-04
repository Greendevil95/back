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

    @GetMapping("")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long Id) {
        return userService.getById(Id);
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody User user) {
        return userService.add(user);
    }

    @PostMapping("/{id}")
    public ResponseEntity addOrganization(@PathVariable(value = "id") Long id, @RequestBody Organization organization ){
        return organizatioService.add(id,organization);
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUserById(@PathVariable(value = "id") Long id, @RequestBody User user){
        return userService.updateById(id,user);
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody User user){
        return userService.delete(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable(value = "id") Long id){
        return userService.deleteById(id);
    }

}
