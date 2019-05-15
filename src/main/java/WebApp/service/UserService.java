package WebApp.service;


import WebApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends CommonService<User> {
    ResponseEntity getAuthUser();

    ResponseEntity getAuthUserId();

    ResponseEntity getInterestsForUserById(Long id);
}