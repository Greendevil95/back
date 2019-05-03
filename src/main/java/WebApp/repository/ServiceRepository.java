package WebApp.repository;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceRepository extends CommonRepository<Service> {
    Page<Service> findByOrganization(Organization organization, Pageable pageable);
    Page<Service> findByReservations(Reservation reservation, Pageable pageable);
    @Query(value = "select AVG(r.rating) " +
            "from reservation r " +
            "where r.service_id = :serviceId",
            nativeQuery = true)
    Float getRating(@Param("serviceId") Long serviceId);
}
