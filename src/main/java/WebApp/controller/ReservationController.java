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

import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController extends AbstractController<Reservation, ReservationServiceImpl> {
    @Autowired
    ReservationService reservationService;

    public ReservationController(ReservationServiceImpl service) {
        super(service);
    }

    @PostMapping("/addrating")
    public ResponseEntity<Iterable<User>> addRating(@RequestBody Reservation reservation) {
        return reservationService.addRating(reservation);
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<Optional<User>> getOwnerReservationById(@PathVariable(value = "id") Long id) {
        return reservationService.getOwnerReservation(id);
    }

    @GetMapping("/{id}/service")
    public ResponseEntity<Optional<Service>> getServiceForReservationById(@PathVariable(value = "id") Long id) {
        return reservationService.getServiceForReservation(id);
    }
}
