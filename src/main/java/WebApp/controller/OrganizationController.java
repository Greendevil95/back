package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.entity.Service;
import WebApp.entity.User;
import WebApp.entity.response.EntityResponse;
import WebApp.service.OrganizatioService;
import WebApp.service.OrganizationServiceImpl;
import WebApp.service.forStatistics.OrganizationStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/organizations")
public class OrganizationController extends AbstractController<Organization,OrganizationServiceImpl> {
    public OrganizationController(OrganizationServiceImpl service) {
        super(service);
    }

    @Autowired
    OrganizatioService organizatioService;

    @GetMapping("/{id}/user")
    public ResponseEntity<EntityResponse<User>> getOwnerOrganizationById(@PathVariable(value = "id") Long id,
                                                                            @RequestParam(value = "page", required = false) Integer page,
                                                                            @RequestParam(value = "field" , required = false) String fieldForSort,
                                                                            @RequestParam(value = "search", required = false) String search){
        return organizatioService.getOwnerOrganization(id,page,fieldForSort,search);
    }

    @GetMapping("/{id}/services")
    public ResponseEntity<EntityResponse<Service>> getReservationForUserById(@PathVariable(value = "id") Long id,
                                                                             @RequestParam(value = "page", required = false) Integer page,
                                                                             @RequestParam(value = "field" , required = false) String fieldForSort,
                                                                             @RequestParam(value = "search", required = false) String search){
        return organizatioService.getServicesForOrganizationById(id,page,fieldForSort,search);
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<OrganizationStatistics> getStatistics(@PathParam(value = "id") Long id){
        return organizatioService.getStatisticsById(id);
    }

}
