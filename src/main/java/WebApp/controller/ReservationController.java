package WebApp.controller;

import WebApp.entity.Reservation;
import WebApp.entity.User;
import WebApp.service.ReservationService;
import WebApp.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
