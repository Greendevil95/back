package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.service.OrganizatioService;
import WebApp.service.OrganizationServiceImpl;
import WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OrganizationController extends AbstractController<Organization,OrganizationServiceImpl> {

    public OrganizationController(OrganizationServiceImpl service) {
        super(service);
    }

    @Autowired
    OrganizatioService organizatioService;

    @Autowired
    UserService userService;

}
