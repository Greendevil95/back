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

    @GetMapping("")
    public ResponseEntity<Iterable<Organization>> getAllOrganization() {
        return organizatioService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganization(@PathVariable(value = "id") Long Id) {
        return organizatioService.getById(Id);
    }

    @PutMapping
    public ResponseEntity updateOrganization(@RequestBody Organization organization) {
        return organizatioService.update(organization);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOrganizationById(@PathVariable(value = "id") Long id, @RequestBody Organization organization){
        return organizatioService.updateById(id,organization);
    }

    @DeleteMapping
    public ResponseEntity deleteOrganization(@RequestBody Organization organization){
        return organizatioService.delete(organization);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrganizationById(@PathVariable(value = "id") Long id){
        return organizatioService.deleteById(id);
    }







}
