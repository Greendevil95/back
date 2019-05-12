package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.response.EntityResponse;
import WebApp.service.ReservationService;
import WebApp.service.ServiceService;
import WebApp.service.ServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/services")
public class ServiceController extends AbstractController<Service, ServiceServiceImpl> {

    @Autowired
    ServiceService serviceService;

    @Autowired
    ReservationService reservationService;

    public ServiceController(ServiceServiceImpl service) {
        super(service);
    }

    @GetMapping("/{id}/organization")
    public ResponseEntity<Optional<Organization>> getOrganizationForServiceById(@PathVariable(value = "id") Long id) {
        return serviceService.getOrganizationForServiceById(id);
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<EntityResponse<Reservation>> getReservationForServiceById(@PathVariable(value = "id") Long id,
                                                                                    @RequestParam(value = "page", required = false) Integer page,
                                                                                    @RequestParam(value = "pagesize", required = false) Integer pageSize,
                                                                                    @RequestParam(value = "field", required = false) String fieldForSort,
                                                                                    @RequestParam(value = "search", required = false) String search) {
        search = search == null ? "service.id:" + id : search + ",andservice.id:" + id;
        return reservationService.getAll(page, pageSize, fieldForSort, search);
    }

    @GetMapping("/{id}/reservations/status")
    public ResponseEntity<EntityResponse<Reservation>> getReservationForServiceByIdStatus(@PathVariable(value = "id") Long id,
                                                                                          @RequestParam(value = "page", required = false) Integer page,
                                                                                          @RequestParam(value = "pagesize", required = false) Integer pageSize,
                                                                                          @RequestParam(value = "field", required = false) String fieldForSort,
                                                                                          @RequestParam(value = "search", required = false) String search) {

        return reservationService.getReservationForServiceByIdStatus(id, page, pageSize, fieldForSort, search);
    }

    @GetMapping("/{id}/reservations/status/count")
    public ResponseEntity<EntityResponse<Reservation>> getReservationForServiceByIdStatus(@PathVariable(value = "id") Long id,
                                                                                          @RequestParam(value = "search", required = false) String search) {

        return reservationService.getReservationForServiceByIdStatusCount(id, search);
    }


}
