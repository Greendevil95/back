package WebApp.service;

import WebApp.entity.Role;
import WebApp.entity.User;
import WebApp.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserServiceImpl extends AbstractService<User, UserRepository> implements UserService  {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity add(User user) {
        if (!userRepository.findByEmail(user.getEmail()).isPresent()) {
            hashPass(user);
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            return ResponseEntity.ok("Registration user with email " + user.getEmail() + " successful!");
        } else {
            return ResponseEntity.badRequest().body("User with this email: " + user.getEmail()+ " already exists!");
        }
    }

    @Override
    public ResponseEntity<User> getById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            return ResponseEntity.ok(user);
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Iterable<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @Override
    public ResponseEntity update(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            User updateUser = userRepository.findByEmail(user.getEmail()).get();
            user.setId(updateUser.getId());
            hashPass(user);
            userRepository.save(user);
            return ResponseEntity.ok("Data for user with email " + user.getEmail() + " was refreshing!");
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity updateById(Long id, User user) {
        if (userRepository.findById(id).isPresent()) {
            user.setId(id);
            hashPass(user);
            userRepository.save(user);
            return ResponseEntity.ok("Your data was refreshing!");
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity delete(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            userRepository.deleteByEmail(user.getEmail());
            return ResponseEntity.ok("User with email "+ user.getEmail() + " was delete.");
        }
        else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity deleteById(Long id) {
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
            return ResponseEntity.ok("User with Id " + id + " was delete.");
        }
        else return ResponseEntity.notFound().build();
    }

    private void hashPass(User user){
        String pass = user.getPassword();
        String md5pass = DigestUtils.md5Hex(pass);
        user.setPassword(md5pass);
    }

}
