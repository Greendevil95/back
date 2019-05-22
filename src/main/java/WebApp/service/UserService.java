package WebApp.service;


import WebApp.entity.User;
import WebApp.entity.enums.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService extends CommonService<User> {
    ResponseEntity getAuthUser();

    ResponseEntity getAuthUserId();

    Map<Category, Integer> getInterestsForUserById(Long id);

    ResponseEntity setVipForAuthUser();
}