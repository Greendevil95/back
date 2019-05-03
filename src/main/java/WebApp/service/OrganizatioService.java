package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.User;
import WebApp.entity.response.EntityResponse;
import WebApp.service.forStatistics.OrganizationStatistics;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrganizatioService extends CommonService<Organization> {
    ResponseEntity<EntityResponse<User>> getOwnerOrganization(Long id, Integer page, String fieldForSort, String search);
    ResponseEntity<EntityResponse<WebApp.entity.Service>>getServicesForOrganizationById(Long id, Integer page, String fieldForSort, String search);
    ResponseEntity<OrganizationStatistics> getStatisticsById(Long id);
}
