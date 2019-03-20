package WebApp.controller;

import WebApp.entity.User;
import WebApp.service.OrganizatioService;
import WebApp.service.UserService;
import WebApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/principal")
    public ResponseEntity<Iterable<User>> getPrincipal() {
        return userService.getPrincipal();
    }

}
