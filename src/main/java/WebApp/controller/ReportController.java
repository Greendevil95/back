package WebApp.controller;

import WebApp.entity.Report;
import WebApp.entity.Service;
import WebApp.entity.User;
import WebApp.service.ReportService;
import WebApp.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController extends AbstractController<Report, ReportServiceImpl> {
    @Autowired
    ReportService reportService;

    public ReportController(ReportServiceImpl service) {
        super(service);
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
        return reportService.getUserByReportId(id);
    }

    @GetMapping("/{id}/service")
    public ResponseEntity<Service> getServiceById(@PathVariable(value = "id") Long id) {
        return reportService.getServiceByReportId(id);
    }

    @GetMapping("/{id}/service/owner")
    public ResponseEntity<User> getOwnerService(@PathVariable(value = "id") Long id) {
        return reportService.getOwnerOrganizationByReportId(id);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity setStatusForReportById(@PathVariable(value = "id") Long id,
                                                 @RequestParam(value = "status", required = false) Boolean status) {
        return reportService.setStatusById(id, status);
    }
}
