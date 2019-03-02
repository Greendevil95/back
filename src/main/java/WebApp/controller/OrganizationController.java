package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.service.OrganizatioService;
import WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    OrganizatioService organizatioService;

    @Autowired
    UserService userService;



}
