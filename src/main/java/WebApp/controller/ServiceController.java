package WebApp.controller;

import WebApp.entity.Service;
import WebApp.service.ServiceServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceController extends AbstractController<Service, ServiceServiceImpl> {

    public ServiceController(ServiceServiceImpl service) {
        super(service);
    }
}
