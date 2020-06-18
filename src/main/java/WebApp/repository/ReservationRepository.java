package WebApp.repository;

import WebApp.entity.*;
import WebApp.entity.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

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


    @Query(value = "select * " +
            "from reservation " +
            "where user_id = :userId " +
            "and service_id = :serviceId " +
            "and rating != 0",
            nativeQuery = true)
    Optional<Reservation> checkReservation(@Param("userId") Long userId,
                                           @Param("serviceId") Long serviceId);


    @Query(value = "select * "
            + "from reservation " +
            "where rating > 0 " +
            "order by user_id",
            nativeQuery = true)
    List<Reservation> findAllWithRating();


    @Query(value = "Select COUNT(r.*) " +
            "from reservation r " +
            "where r.user_id = :userId " +
            "and r.rating > 0",
            nativeQuery = true)
    Integer findUserRaitingCount(@Param("userId") long userId);


}
