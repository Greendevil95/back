package WebApp.repository;

import WebApp.entity.Organization;
import WebApp.entity.Service;
import WebApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrganizationRepository extends CommonRepository<Organization> {
    @Query(value = "select AVG(r.rating) " +
            "from reservation r " +
            "inner join service s on s.id = r.service_id " +
            "where s.organization_id = :organizationId " +
            "and r.rating > 0",
            nativeQuery = true)
    Float getRating(@Param("organizationId") Long organizationId);

    Page<Organization> findByUser(User user, Pageable pageable);

    Optional<Organization> findByServices(Service service);

    @Query(value = "select COUNT (r.*) " +
            "from reservation r " +
            "inner join service s on s.id = r.service_id " +
            "inner join organization o on s.organization_id = o.id " +
            "where s.organization_id = :organizationId " +
            "and r.status=:status",
            nativeQuery = true)
    Integer getCountReservationWithStatus(@Param("organizationId") Long organizationId,
                                          @Param("status") String status);

}
