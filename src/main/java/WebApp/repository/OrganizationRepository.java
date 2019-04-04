package WebApp.repository;


import WebApp.entity.Organization;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface OrganizationRepository extends CommonRepository<Organization> {
    List<Organization> findAllByRating (float rating, Pageable pageable);
}
