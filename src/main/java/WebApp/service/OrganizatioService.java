package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.User;
import WebApp.service.forStatistics.OrganizationStatistics;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface OrganizatioService extends CommonService<Organization> {
    ResponseEntity<Optional<User>> getOwnerOrganization(Long id);

    ResponseEntity<OrganizationStatistics> getStatisticsById(Long id);

    ResponseEntity getCountServiceForOrganizationByIdWithStatus(Long id, String status);
}
