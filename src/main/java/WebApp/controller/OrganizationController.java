package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.service.OrganizationServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations")
public class OrganizationController extends AbstractController<Organization,OrganizationServiceImpl> {

    public OrganizationController(OrganizationServiceImpl service) {
        super(service);
    }

}
