package WebApp.service;


import WebApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResponseEntity insertUser(User user);

    ResponseEntity<User> getUserById(Long Id);

    ResponseEntity refreshUser(User user);





}
