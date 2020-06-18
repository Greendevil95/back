package WebApp.service;

import WebApp.entity.Report;
import WebApp.entity.User;
import WebApp.entity.enums.Role;
import WebApp.entity.response.EntityResponse;
import WebApp.repository.ReportRepository;
import WebApp.repository.ServiceRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity add(Report report) {
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        report.setUser(authUser);

        Optional<WebApp.entity.Service> service = serviceRepository.findById(report.getService().getId());
        if (!service.isPresent()) {
            return ResponseEntity.badRequest().body("Service add id " + report.getService().getId() + " not found.");
        }
        report.setService(service.get());
        report.setStatus(false);
        reportRepository.save(report);
        return ResponseEntity.ok("Report save");
    }

    @PreAuthorize("hasAuthority('USER') OR hasAuthority('ADMIN')")
    @Override
    public ResponseEntity update(Report report) {
        return updateById(report.getId(), report);
    }

    @PreAuthorize("hasAuthority('USER') OR hasAuthority('ADMIN')")
    @Override
    public ResponseEntity updateById(Long id, Report report) {

        Optional<Report> updateReport = reportRepository.findById(id);
        if (!updateReport.isPresent()) {
            return ResponseEntity.badRequest().body("Report add id + " + id + " not found.");
        }

        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> authUser = userRepository.findByEmail(authUserName);

        if (!isAuthUser(updateReport.get().getUser()) || !authUser.get().getRoles().contains(Role.ADMIN)) {
            return ResponseEntity.badRequest().body("This is not you report");
        }

        Optional<WebApp.entity.Service> service = serviceRepository.findById(report.getService().getId());
        if (!service.isPresent()) {
            return ResponseEntity.badRequest().body("Service add id " + report.getService().getId() + " not found.");
        }

        updateReport.get().setDescription(report.getDescription());
        updateReport.get().setService(service.get());
        reportRepository.save(updateReport.get());
        return ResponseEntity.ok("Report add id " + id + " was update.");
    }

    @PreAuthorize("hasAuthority('USER') OR hasAuthority('ADMIN')")
    @Override
    public ResponseEntity delete(Report report) {

        Optional<Report> deleteReport = reportRepository.findById(report.getId());
        if (!deleteReport.isPresent()) {
            return ResponseEntity.badRequest().body("Report add id + " + report.getId() + " not found.");
        }

        if (!isAuthUser(deleteReport.get().getUser())) {
            return ResponseEntity.badRequest().body("This is not you report");
        }

        reportRepository.delete(report);
        return ResponseEntity.ok("Report add id " + report.getId() + " was delete.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity setStatusById(Long id, Boolean status) {
        Optional<Report> report = reportRepository.findById(id);
        if (!report.isPresent()) {
            return ResponseEntity.badRequest().body("Report add id " + id + " not found.");
        }
        report.get().setStatus(status);
        reportRepository.save(report.get());
        return ResponseEntity.ok().body("Status for report add id " + id + " was changed.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> getUserByReportId(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (!report.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(report.get().getUser());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WebApp.entity.Service> getServiceByReportId(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (!report.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(report.get().getService());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> getOwnerOrganizationByReportId(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (!report.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(report.get().getService().getOrganization().getUser());
    }


    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<EntityResponse<Report>> getAll(Integer page, Integer pageSize, String fieldForSort, String search) {
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> authUser = userRepository.findByEmail(authUserName);

        if (!authUser.get().getRoles().contains(Role.ADMIN)) {
            search = search == null ? "user.id:" + authUser.get().getId() : search + ",anduser.id:" + authUser.get().getId();
        }

        return super.getAll(page, pageSize, fieldForSort, search);
    }
}
