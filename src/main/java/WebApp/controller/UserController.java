package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.User;
import WebApp.entity.response.EntityResponse;
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

    @GetMapping("/auth")
    public ResponseEntity<Iterable<User>> getAuthUser() {
        return userService.getAuthUser();
    }

    @GetMapping("/auth/id")
    public ResponseEntity<Iterable<User>> getAuthUserId() {
        return userService.getAuthUserId();
    }

    @GetMapping("/{id}/organizations")
    public ResponseEntity<EntityResponse<Organization>> getOrganizationsForUserById(@PathVariable(value = "id") Long id,
                                                                                    @RequestParam(value = "page", required = false) Integer page,
                                                                                    @RequestParam(value = "field" , required = false) String fieldForSort,
                                                                                    @RequestParam(value = "search", required = false) String search){
        return userService.getOrganizationForUserId(id,page,fieldForSort,search);
    }

    @GetMapping("/organizations")
    public ResponseEntity<EntityResponse<Organization>> getOrganizationsForAuthUser(@RequestParam(value = "page", required = false) Integer page,
                                                                              @RequestParam(value = "field" , required = false) String fieldForSort,
                                                                              @RequestParam(value = "search", required = false) String search){
        return userService.getOrganizationForAuthUser(page,fieldForSort,search);
    }

    @GetMapping("/{id}/reservation")
    public ResponseEntity<EntityResponse<Reservation>> getReservationForUserById(@PathVariable(value = "id") Long id,
                                                                           @RequestParam(value = "page", required = false) Integer page,
                                                                           @RequestParam(value = "field" , required = false) String fieldForSort,
                                                                           @RequestParam(value = "search", required = false) String search){
        return userService.getReservationForUserById(id,page,fieldForSort,search);
    }

    @GetMapping("/reservation")
    public ResponseEntity<EntityResponse<Reservation>> getReservationForAuthUser(@RequestParam(value = "page", required = false) Integer page,
                                                                           @RequestParam(value = "field" , required = false) String fieldForSort,
                                                                           @RequestParam(value = "search", required = false) String search){
        return userService.getReservationForAuthUser(page,fieldForSort,search);
    }
}
