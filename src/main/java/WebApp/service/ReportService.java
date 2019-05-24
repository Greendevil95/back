package WebApp.service;

import WebApp.entity.Report;
import WebApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReportService extends CommonService<Report> {

    ResponseEntity setStatusById(Long id, Boolean status);

    ResponseEntity<User> getUserByReportId(Long id);

    ResponseEntity<User> getOwnerOrganizationByReportId(Long id);

    ResponseEntity<WebApp.entity.Service> getServiceByReportId(Long id);
}
