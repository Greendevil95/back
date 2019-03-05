package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.entity.User;
import WebApp.service.OrganizatioService;
import WebApp.service.UserService;
import WebApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User,UserServiceImpl> {

    public UserController(UserServiceImpl service) {
        super(service);
    }





    @Autowired
    private UserService userService;

    @Autowired
    OrganizatioService organizatioService;


    @PostMapping
    public ResponseEntity add(@RequestBody User user) {
        return userService.add(user);
    }

    @PostMapping("/{id}")
    public ResponseEntity addOrganization(@PathVariable(value = "id") Long id, @RequestBody Organization organization ){
        return organizatioService.add(id,organization);
    }
}
