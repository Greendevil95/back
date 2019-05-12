package WebApp.repository;

import WebApp.entity.Reservation;
import WebApp.entity.ReservationStatus;
import WebApp.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepository extends CommonRepository<Reservation> {
    Page<Reservation> findByServiceAndStatus(Service service, ReservationStatus reservationStatus, Pageable pageable);

    Integer countByServiceAndStatus(Service service, ReservationStatus reservationStatus);
}
