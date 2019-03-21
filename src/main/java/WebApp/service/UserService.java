package WebApp.service;


import WebApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface UserService extends CommonService<User> {

    ResponseEntity getPrincipal();

    User getByEmail(String email);
}