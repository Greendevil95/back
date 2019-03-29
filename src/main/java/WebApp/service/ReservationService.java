package WebApp.service;

import WebApp.entity.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService extends CommonService<Reservation> {
    ResponseEntity addRating(Reservation reservation);
}
