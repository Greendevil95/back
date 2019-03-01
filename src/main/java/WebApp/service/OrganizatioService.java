package WebApp.service;

import WebApp.entity.Organization;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrganizatioService {
    ResponseEntity addOrganization(Long userId, Organization organization);
}
