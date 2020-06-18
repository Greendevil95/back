package WebApp.repository;

import WebApp.entity.Reservation;
import WebApp.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface ServiceRepository extends CommonRepository<Service> {

    Optional<Service> findByReservations(Reservation reservation);

    @Query(value = "select AVG(r.rating) " +
            "from reservation r " +
            "where r.service_id = :serviceId " +
            "and r.rating > 0",
            nativeQuery = true)
    Float getRating(@Param("serviceId") Long serviceId);


    @Query( value = "select  * "
            + "from service "
            + "where service.rating > 4 "
            + "order by rating DESC",
            nativeQuery = true)
    Page<Service> findAllWithGoodRat(Specification<Service> var1, Pageable var2);
}
