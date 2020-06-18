package WebApp.service;

import WebApp.entity.AbstractEntity;
import WebApp.entity.Reservation;
import WebApp.entity.User;
import WebApp.entity.enums.ReservationStatus;
import WebApp.entity.response.EntityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface ReservationService extends CommonService<Reservation> {
    ResponseEntity addRating(Reservation reservation);

    ResponseEntity<Optional<User>> getOwnerReservation(Long id);

    ResponseEntity<Optional<WebApp.entity.Service>> getServiceForReservation(Long id);

    ResponseEntity setStatusById(Long id, ReservationStatus reservationStatus);

    ResponseEntity<EntityResponse<Reservation>> getReservationForServiceByIdStatus(Long id, Integer page, Integer pageSize, String fieldForSort, String status);

    ResponseEntity getReservationForServiceByIdStatusCount(Long id, String status);

    ResponseEntity getReservationForUserByIdStatusCount(Long id, String status);

    ResponseEntity <Optional<Reservation>> checkReservation(Long id);

   /* ResponseEntity<List<VectorRating>> getAllRating();*/
}
