package WebApp.controller;

import WebApp.entity.User;
import WebApp.repository.UserRepository;
import WebApp.repository.specifications.SearchCriteria;
import WebApp.repository.specifications.UserSpecification;
import WebApp.repository.specifications.UserSpecificationsBuilder;
import WebApp.service.OrganizatioService;
import WebApp.service.UserService;
import WebApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    UserRepository userRepository;

    @GetMapping("/auth")
    public ResponseEntity<Iterable<User>> getAuthUser() {
        return userService.getAuthUser();
    }

    @GetMapping("/auth/id")
    public ResponseEntity<Iterable<User>> getAuthUserId() {
        return userService.getAuthUserId();
    }

    @GetMapping("/test")
    public ResponseEntity<Iterable<User>> bySpec(){
        UserSpecification userSpecification = new UserSpecification(new SearchCriteria("email", ":", "alex@mail.ru"));
        return userService.bySpec(userSpecification);
    }

    @GetMapping("/spec")
    public List<User> search(@RequestParam(value = "search", required = false) String search) {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<User> spec = builder.build();
        return userRepository.findAll(spec);
    }
}
