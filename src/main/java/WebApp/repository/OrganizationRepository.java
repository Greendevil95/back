package WebApp.repository;


import WebApp.entity.Organization;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationRepository extends CommonRepository<Organization> {
    @Query(value = "select AVG(r.rating) " +
            "from reservation r " +
            "inner join service s on s.id = r.service_id " +
            "where s.organization_id = :organizationId",
        nativeQuery = true)
    Float getRating(@Param("organizationId") Long organizationId);




}
