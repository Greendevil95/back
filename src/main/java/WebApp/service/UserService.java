package WebApp.service;


import WebApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void insertUser(User user);

    ResponseEntity<User> getUserById(Long Id);

    void refreshUser(User user);





}
