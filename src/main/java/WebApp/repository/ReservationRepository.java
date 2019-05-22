package WebApp.repository;

import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.User;
import WebApp.entity.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends CommonRepository<Reservation> {
    Page<Reservation> findByServiceAndStatus(Service service, ReservationStatus reservationStatus, Pageable pageable);

    Integer countByServiceAndStatus(Service service, ReservationStatus reservationStatus);

    Iterable<Reservation> findByUser(User user);

    @Query(value = "select COUNT(r.*) " +
            "from reservation r " +
            "inner join service s on r.service_id = s.id " +
            "inner join organization o on s.organization_id = o.id " +
            "inner join usr u on o.user_id = u.id " +
            "where o.user_id = :userId " +
            "and lower(r.status) = :status ",
            nativeQuery = true)
    Integer getCountForOwnerOrganizationAndStatus(@Param("userId") Long userId,
                                                  @Param("status") String status);
}
