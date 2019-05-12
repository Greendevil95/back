package WebApp.service;

import WebApp.entity.Reservation;
import WebApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ReservationService extends CommonService<Reservation> {
    ResponseEntity addRating(Reservation reservation);

    ResponseEntity<Optional<User>> getOwnerReservation(Long id);

    ResponseEntity<Optional<WebApp.entity.Service>> getServiceForReservation(Long id);

    ResponseEntity setStatus(Reservation reservation);
}
