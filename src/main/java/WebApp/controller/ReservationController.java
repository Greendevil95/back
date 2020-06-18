package WebApp.controller;

import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.User;
import WebApp.entity.enums.ReservationStatus;
import WebApp.service.ReservationService;
import WebApp.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
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

    @PutMapping("/{id}/status")
    public ResponseEntity setStatusForReservationById(@PathVariable(value = "id") Long id,
                                                      @RequestParam(value = "status", required = false) String status) {
        return reservationService.setStatusById(id, ReservationStatus.get(status));
    }

    @GetMapping("{id}/checkReservation")
    public ResponseEntity<Optional<Reservation>> getCheckReservation (@PathVariable(value = "id") Long id){
        return reservationService.checkReservation(id);
    }

    /*@GetMapping("/allRating")
    public ResponseEntity<List<VectorRating>> getAllRating(){
        return reservationService.getAllRating();
    }*/
}
