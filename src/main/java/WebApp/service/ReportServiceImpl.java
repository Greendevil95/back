package WebApp.service;

import WebApp.entity.Report;
import WebApp.entity.User;
import WebApp.repository.ReportRepository;
import WebApp.repository.ServiceRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportServiceImpl extends AbstractService<Report, ReportRepository> implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public ReportServiceImpl(ReportRepository repository) {
        super(repository);
    }

    @Override
    public ResponseEntity add(Report report) {
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        report.setUser(authUser);

        Optional<WebApp.entity.Service> service = serviceRepository.findById(report.getService().getId());
        if (!service.isPresent()) {
            return ResponseEntity.badRequest().body("Service with id " + report.getService().getId() + " not found.");
        }
        report.setService(service.get());

        report.setStatus(false);
        reportRepository.save(report);
        return null;
    }

    @Override
    public ResponseEntity update(Report report) {
        return updateById(report.getId(), report);
    }

    @Override
    public ResponseEntity updateById(Long id, Report report) {

        Optional<Report> updateReport = reportRepository.findById(id);
        if (!updateReport.isPresent()) {
            return ResponseEntity.badRequest().body("Report with id + " + id + " not found.");
        }

        if (!isAuthUser(updateReport.get().getUser())) {
            return ResponseEntity.badRequest().body("This is not you report");
        }

        Optional<WebApp.entity.Service> service = serviceRepository.findById(report.getService().getId());
        if (!service.isPresent()) {
            return ResponseEntity.badRequest().body("Service with id " + report.getService().getId() + " not found.");
        }

        updateReport.get().setDescription(report.getDescription());
        updateReport.get().setService(service.get());
        reportRepository.save(updateReport.get());
        return ResponseEntity.ok("Report with id " + id + " was update.");
    }

    @Override
    public ResponseEntity delete(Report report) {

        Optional<Report> deleteReport = reportRepository.findById(report.getId());
        if (!deleteReport.isPresent()) {
            return ResponseEntity.badRequest().body("Report with id + " + report.getId() + " not found.");
        }

        if (!isAuthUser(deleteReport.get().getUser())) {
            return ResponseEntity.badRequest().body("This is not you report");
        }

        reportRepository.delete(report);
        return ResponseEntity.ok("Report with id " + report.getId() + " was delete.");
    }
}
