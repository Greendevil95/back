package WebApp.service;


import WebApp.entity.Interest;
import WebApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends CommonService<User> {
    ResponseEntity getAuthUser();

    ResponseEntity getAuthUserId();

    Iterable<Interest> getInterestsForUserById(Long id);

    Iterable<Interest> get3InterestsForUserById(Long id);

    ResponseEntity setVipForAuthUser();

    ResponseEntity setState(Long id, String state);
}