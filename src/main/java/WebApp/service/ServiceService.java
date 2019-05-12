package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.Service;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@org.springframework.stereotype.Service
public interface ServiceService extends CommonService<Service> {
    ResponseEntity<Optional<Organization>> getOrganizationForServiceById(Long id);
}
