package WebApp.service;


import WebApp.entity.User;
import WebApp.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{



    @Autowired
    private
    UserRepository userRepository;

    @Override
    public void insertUser(User user) {
        if(!userRepository.findByEmail(user.getEmail()).isPresent()) {
            String pass = user.getPassword();
            String md5pass = DigestUtils.md5Hex(pass);
            user.setPassword(md5pass);
            userRepository.save(user);
        }
        else ResponseEntity.status(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            return ResponseEntity.ok(user);
        }
        else return  ResponseEntity.notFound().build();
    }


    @Override
    public void refreshUser(User user) {
        if(!userRepository.findByEmail(user.getEmail()).isPresent()) {
            User refreshingUser = userRepository.findByEmail(user.getEmail()).get();
            //ResponseEntity.created();
            //userRepository.deleteByEmail(user.getEmail());
            //insertUser(user);
        }
        else ResponseEntity.noContent().build();
    }



    /*private UserEntity createEntity(User user) {
        String mail = user.getEmail();
        String pass = user.getPassword();
        String md5pass = DigestUtils.md5Hex(pass);
        Long id = user.getId();
        entity = new UserEntity(id,mail,md5pass);
        return entity;
    }

    private User createUser(UserEntity entity) {
        String mail = entity.getEmail();
        String pass = entity.getPassword();
        Long id = entity.getId();
        user = new User(id,mail,pass);
        return user;

    }*/
}
