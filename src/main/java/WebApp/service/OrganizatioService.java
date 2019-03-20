package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface OrganizatioService extends CommonService<Organization> {

    ResponseEntity<Optional<Organization>> getAllForUser(User user);
}
