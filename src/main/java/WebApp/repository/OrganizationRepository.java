package WebApp.repository;


import WebApp.entity.Organization;
import WebApp.entity.Service;
import WebApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OrganizationRepository extends CommonRepository<Organization> {
    @Query(value = "select AVG(r.rating) " +
            "from reservation r " +
            "inner join service s on s.id = r.service_id " +
            "where s.organization_id = :organizationId",
        nativeQuery = true)
    Float getRating(@Param("organizationId") Long organizationId);

    Page<Organization> findByUser(User user, Pageable pageable);
    Page<Organization> findByServices(Service service, Pageable pageable);

}
