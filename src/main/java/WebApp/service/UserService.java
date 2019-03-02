package WebApp.service;


import WebApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity addUser(User user);
    ResponseEntity<User> getUserById(Long Id);
    ResponseEntity refreshUser(User user);
    ResponseEntity deleteUser(User user);
    ResponseEntity deleteUserById(Long Id);
    ResponseEntity<Iterable<User>> getAllUsers();
    void save(User user);
}
