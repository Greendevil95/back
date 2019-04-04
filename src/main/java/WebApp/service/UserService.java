package WebApp.service;


import WebApp.entity.User;
import WebApp.repository.specifications.UserSpecification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends CommonService<User> {
    ResponseEntity getAuthUser();
    ResponseEntity getAuthUserId();
    ResponseEntity<Iterable<User>> bySpec(UserSpecification userSpecification);
}