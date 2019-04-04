package WebApp.controller;

import WebApp.entity.Reservation;
import WebApp.service.ReservationServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController extends AbstractController<Reservation,ReservationServiceImpl> {
    public ReservationController(ReservationServiceImpl service) {
        super(service);
    }
}
