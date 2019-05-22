package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.User;
import WebApp.entity.enums.Category;
import WebApp.entity.response.EntityResponse;
import WebApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User, UserServiceImpl> {

    @Autowired
    private UserService userService;
    @Autowired
    private OrganizatioService organizatioService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ReservationService reservationService;

    public UserController(UserServiceImpl service) {
        super(service);
    }

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
                                                                                    @RequestParam(value = "pagesize", required = false) Integer pageSize,
                                                                                    @RequestParam(value = "field", required = false) String fieldForSort,
                                                                                    @RequestParam(value = "search", required = false) String search) {
        search = search == null ? "user.id:" + id : search + ",anduser.id:" + id;
        return organizatioService.getAll(page, pageSize, fieldForSort, search);
    }

    @GetMapping("/organizations")
    public ResponseEntity<EntityResponse<Organization>> getOrganizationsForAuthUser(@RequestParam(value = "page", required = false) Integer page,
                                                                                    @RequestParam(value = "pagesize", required = false) Integer pageSize,
                                                                                    @RequestParam(value = "field", required = false) String fieldForSort,
                                                                                    @RequestParam(value = "search", required = false) String search) {
        Long id = Long.parseLong(userService.getAuthUserId().getBody().toString());
        return getOrganizationsForUserById(id, page, pageSize, fieldForSort, search);
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<EntityResponse<Reservation>> getReservationForUserById(@PathVariable(value = "id") Long id,
                                                                                 @RequestParam(value = "page", required = false) Integer page,
                                                                                 @RequestParam(value = "pagesize", required = false) Integer pageSize,
                                                                                 @RequestParam(value = "field", required = false) String fieldForSort,
                                                                                 @RequestParam(value = "search", required = false) String search) {
        search = search == null ? "user.id:" + id : search + ",anduser.id:" + id;
        return reservationService.getAll(page, pageSize, fieldForSort, search);
    }

    @GetMapping("/reservations")
    public ResponseEntity<EntityResponse<Reservation>> getReservationForAuthUser(@RequestParam(value = "page", required = false) Integer page,
                                                                                 @RequestParam(value = "pagesize", required = false) Integer pageSize,
                                                                                 @RequestParam(value = "field", required = false) String fieldForSort,
                                                                                 @RequestParam(value = "search", required = false) String search) {
        Long id = Long.parseLong(userService.getAuthUserId().getBody().toString());
        return getReservationForUserById(id, page, pageSize, fieldForSort, search);
    }

    @GetMapping("/{id}/interests")
    public ResponseEntity getUserInterestTags(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(userService.getInterestsForUserById(id));
    }

    @GetMapping("/{id}/interests/services")
    public ResponseEntity<EntityResponse<Service>> getServicesForUserWithIdByInterest(@PathVariable(value = "id") Long id,
                                                                                      @RequestParam(value = "page", required = false) Integer page,
                                                                                      @RequestParam(value = "pagesize", required = false) Integer pageSize,
                                                                                      @RequestParam(value = "field", required = false) String fieldForSort,
                                                                                      @RequestParam(value = "search", required = false) String search) {

        Map<Category, Integer> map = userService.getInterestsForUserById(id);

        if (map.size() > 1) {
            String firstInteres = Category.get((Category) map.keySet().toArray()[0]);
            search = search == null ? "category:" + firstInteres : search + ",andcategory:" + firstInteres;
            if (map.size() > 2) {
                String secondInteres = Category.get((Category) map.keySet().toArray()[1]);
                search = search + ",orcategory:" + secondInteres;
                if (map.size() > 3) {
                    String thirdInteres = Category.get((Category) map.keySet().toArray()[2]);
                    search = search + ",orcategory:" + thirdInteres;
                }
            }

        }

        return serviceService.getAll(page, pageSize, fieldForSort, search);
    }


    @GetMapping("/interests/services")
    public ResponseEntity<EntityResponse<Service>> getServicesForAuthUserByInterest(@RequestParam(value = "page", required = false) Integer page,
                                                                                    @RequestParam(value = "pagesize", required = false) Integer pageSize,
                                                                                    @RequestParam(value = "field", required = false) String fieldForSort,
                                                                                    @RequestParam(value = "search", required = false) String search) {
        Long id = Long.parseLong(userService.getAuthUserId().getBody().toString());
        return getServicesForUserWithIdByInterest(id, page, pageSize, fieldForSort, search);
    }

    @GetMapping("/{id}/reservations/status/count")
    public ResponseEntity getReservationForUserByIdStatus(@PathVariable(value = "id") Long id,
                                                          @RequestParam(value = "status", required = false) String status) {
        return reservationService.getReservationForUserByIdStatusCount(id, status);
    }

    @PostMapping("/auth/vip")
    public ResponseEntity setVipForAuthUser(){
        return userService.setVipForAuthUser();
    }


}
