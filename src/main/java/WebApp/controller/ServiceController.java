package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.response.EntityResponse;
import WebApp.service.ServiceService;
import WebApp.service.ServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
public class ServiceController extends AbstractController<Service, ServiceServiceImpl> {

    public ServiceController(ServiceServiceImpl service) {
        super(service);
    }

    @Autowired
    ServiceService serviceService;


    @GetMapping("/{id}/organization")
    public ResponseEntity<EntityResponse<Organization>> getOwnerOrganizationById(@PathVariable(value = "id") Long id,
                                                                                 @RequestParam(value = "page", required = false) Integer page,
                                                                                 @RequestParam(value = "field" , required = false) String fieldForSort,
                                                                                 @RequestParam(value = "search", required = false) String search){
        return serviceService.getOrganizationForServiceById(id,page,fieldForSort,search);
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<EntityResponse<Reservation>> getReservationForUserById(@PathVariable(value = "id") Long id,
                                                                                 @RequestParam(value = "page", required = false) Integer page,
                                                                                 @RequestParam(value = "field" , required = false) String fieldForSort,
                                                                                 @RequestParam(value = "search", required = false) String search){
        return serviceService.getReservationForServiceById(id,page,fieldForSort,search);
    }



}
