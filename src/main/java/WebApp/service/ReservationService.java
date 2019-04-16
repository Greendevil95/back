package WebApp.service;

import WebApp.entity.Reservation;
import WebApp.entity.User;
import WebApp.entity.response.EntityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService extends CommonService<Reservation> {
    ResponseEntity addRating(Reservation reservation);
    ResponseEntity<EntityResponse<User>> getOwnerReservation(Long id, Integer page, String fieldForSort, String search);
    ResponseEntity<EntityResponse<WebApp.entity.Service>> getServiceForReservation(Long id, Integer page, String fieldForSort, String search);
}
