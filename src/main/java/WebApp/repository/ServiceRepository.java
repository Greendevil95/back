package WebApp.repository;

import WebApp.entity.Reservation;
import WebApp.entity.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ServiceRepository extends CommonRepository<Service> {
    Optional<Service> findByReservations(Reservation reservation);

    @Query(value = "select AVG(r.rating) " +
            "from reservation r " +
            "where r.service_id = :serviceId",
            nativeQuery = true)
    Float getRating(@Param("serviceId") Long serviceId);
}
