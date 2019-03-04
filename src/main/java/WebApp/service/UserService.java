package WebApp.service;


import WebApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    ResponseEntity add(User user);


    ResponseEntity<User> getById(Long Id);

    ResponseEntity<Iterable<User>> getAll();


    ResponseEntity update(User user);

    ResponseEntity updateById(Long id, User user);


    ResponseEntity delete(User user);

    ResponseEntity deleteById(Long Id);

}