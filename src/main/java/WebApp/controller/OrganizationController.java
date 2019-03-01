package WebApp.controller;

import WebApp.entity.Organization;
import WebApp.service.OrganizatioService;
import WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{userId}/organization")
public class OrganizationController {

    @Autowired
    OrganizatioService organizatioService;

    @Autowired
    UserService userService;

//    @GetMapping
//    public ResponseEntity allUser(){
//        return userService.getAllUser();
//    }


    @PostMapping
    public ResponseEntity addOrganization(@PathVariable(value = "userId") Long userId, @RequestBody Organization organization){
        return organizatioService.addOrganization(userId,organization);
    }

}
