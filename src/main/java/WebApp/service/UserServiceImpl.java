package WebApp.service;


import WebApp.entity.User;
import WebApp.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity addUser(User user) {
        if (!userRepository.findByEmail(user.getEmail()).isPresent()) {
            hashPass(user);
            userRepository.save(user);
            return ResponseEntity.ok("Registration successful!");
        } else {
            return ResponseEntity.badRequest().body("User with this email: " + user.getEmail()+ " already exists!");
        }
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            return ResponseEntity.ok(user);
        } else return ResponseEntity.notFound().build();
    }


    @Override
    public ResponseEntity refreshUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            User refreshingUser = userRepository.findByEmail(user.getEmail()).get();
            user.setId(refreshingUser.getId());
            hashPass(user);
            userRepository.save(user);
            return ResponseEntity.ok("Your data was refreshing!");
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity deleteUser(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            userRepository.deleteByEmail(user.getEmail());
            return ResponseEntity.ok("User with email "+ user.getEmail() + " was delete.");
        }
        else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Iterable<User>> getAllUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    private void hashPass(User user){
        String pass = user.getPassword();
        String md5pass = DigestUtils.md5Hex(pass);
        user.setPassword(md5pass);
    }

}
