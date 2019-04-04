package WebApp.controller;

import WebApp.entity.User;
import WebApp.repository.specifications.SearchCriteria;
import WebApp.repository.specifications.UserSpecification;
import WebApp.service.OrganizatioService;
import WebApp.service.UserService;
import WebApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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

    @GetMapping("/auth")
    public ResponseEntity<Iterable<User>> getAuthUser() {
        return userService.getAuthUser();
    }

    @GetMapping("/auth/id")
    public ResponseEntity<Iterable<User>> getAuthUserId() {
        return userService.getAuthUserId();
    }

    @GetMapping("test")
    public ResponseEntity<Iterable<User>> bySpec(){
        UserSpecification userSpecification = new UserSpecification(new SearchCriteria("email", ":", "alex@mail.ru"));
        return userService.bySpec(userSpecification);
    }
}
