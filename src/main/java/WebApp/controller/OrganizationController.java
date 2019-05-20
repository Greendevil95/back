package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.User;
import WebApp.entity.response.EntityResponse;
import WebApp.service.*;
import WebApp.service.forStatistics.OrganizationStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
@RequestMapping("/organizations")
public class OrganizationController extends AbstractController<Organization, OrganizationServiceImpl> {
    @Autowired
    OrganizatioService organizatioService;

    @Autowired
    UserService userService;

    @Autowired
    ServiceService serviceService;

    @Autowired
    ReservationService reservationService;

    public OrganizationController(OrganizationServiceImpl service) {
        super(service);
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<Optional<User>> getOwnerOrganizationById(@PathVariable(value = "id") Long id) {
        return organizatioService.getOwnerOrganization(id);
    }

    @GetMapping("/{id}/services")
    public ResponseEntity<EntityResponse<Service>> getServicesForOrganizationById(@PathVariable(value = "id") Long id,
                                                                                  @RequestParam(value = "page", required = false) Integer page,
                                                                                  @RequestParam(value = "pagesize", required = false) Integer pageSize,
                                                                                  @RequestParam(value = "field", required = false) String fieldForSort,
                                                                                  @RequestParam(value = "search", required = false) String search) {
        search = search == null ? "organization.id:" + id : search + ",andorganization.id:" + id;
        return serviceService.getAll(page, pageSize, fieldForSort, search);
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<OrganizationStatistics> getStatistics(@PathParam(value = "id") Long id) {
        return organizatioService.getStatisticsById(id);
    }

    @GetMapping("/{id}/services/statuscount")
    public ResponseEntity<OrganizationStatistics> getStatistics(@PathVariable(value = "id") Long id,
                                                                @RequestParam(value = "status", required = false) String status) {
        return organizatioService.getCountServiceForOrganizationByIdWithStatus(id, status);
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<EntityResponse<Reservation>> getReservationForOrganizationById(@PathVariable(value = "id") Long id,
                                                                                         @RequestParam(value = "page", required = false) Integer page,
                                                                                         @RequestParam(value = "pagesize", required = false) Integer pageSize,
                                                                                         @RequestParam(value = "field", required = false) String fieldForSort,
                                                                                         @RequestParam(value = "search", required = false) String search) {
        search = search == null ? "service.organization.id:" + id : "andservice.organization.id:" + id;
        return reservationService.getAll(page, pageSize, fieldForSort, search);
    }
}
