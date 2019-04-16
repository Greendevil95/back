package WebApp.repository;

import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepository extends CommonRepository<Reservation> {
    Page<Reservation> findByUser(User user, Pageable pageable);
    Page<Reservation> findByService(Service service, Pageable pageable);
}
