package WebApp.controller;

import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.User;
import WebApp.entity.response.EntityResponse;
import WebApp.service.ReservationService;
import WebApp.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController extends AbstractController<Reservation,ReservationServiceImpl> {
    public ReservationController(ReservationServiceImpl service) {
        super(service);
    }

    @Autowired
    ReservationService reservationService;

    @PostMapping("/addrating")
    public ResponseEntity<Iterable<User>> addRating(@RequestBody Reservation reservation) {
        return reservationService.addRating(reservation);
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<EntityResponse<User>> getOwnerReservationById(@PathVariable(value = "id") Long id,
                                                                         @RequestParam(value = "page", required = false) Integer page,
                                                                         @RequestParam(value = "field" , required = false) String fieldForSort,
                                                                         @RequestParam(value = "search", required = false) String search){
        return reservationService.getOwnerReservation(id,page,fieldForSort,search);
    }

    @GetMapping("/{id}/service")
    public ResponseEntity<EntityResponse<Service>> getServiceForReservationById(@PathVariable(value = "id") Long id,
                                                                             @RequestParam(value = "page", required = false) Integer page,
                                                                             @RequestParam(value = "field" , required = false) String fieldForSort,
                                                                             @RequestParam(value = "search", required = false) String search){
        return reservationService.getServiceForReservation(id,page,fieldForSort,search);
    }
}
