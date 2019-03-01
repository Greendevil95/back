package WebApp.repository;


import WebApp.entity.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
}
