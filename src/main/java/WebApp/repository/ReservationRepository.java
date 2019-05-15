package WebApp.repository;

import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.User;
import WebApp.entity.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepository extends CommonRepository<Reservation> {
    Page<Reservation> findByServiceAndStatus(Service service, ReservationStatus reservationStatus, Pageable pageable);

    Integer countByServiceAndStatus(Service service, ReservationStatus reservationStatus);

    Iterable<Reservation> findByUser(User user);
}
