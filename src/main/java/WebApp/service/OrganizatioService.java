package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface OrganizatioService {

    ResponseEntity add(Long id, Organization organization);


    ResponseEntity<Iterable<Organization>> getAll();

    ResponseEntity<Optional<Organization>> getAllForUser(User user);

    ResponseEntity<Organization> getById(Long id);


    ResponseEntity updateById(Long id, Organization organization);

    ResponseEntity update(Organization organization);


    ResponseEntity deleteById(Long id);

    ResponseEntity delete(Organization organization);
}
