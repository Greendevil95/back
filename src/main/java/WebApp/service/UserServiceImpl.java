package WebApp.service;

import WebApp.entity.Role;
import WebApp.entity.State;
import WebApp.entity.User;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl extends AbstractService<User, UserRepository> implements UserService  {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity getAuthUser(){
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        return ResponseEntity.ok(authUser);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity getAuthUserId(){
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        return ResponseEntity.ok(authUser.getId());
    }


    @Override
    public ResponseEntity add(User user) {
        if (!userRepository.findByEmail(user.getEmail()).isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(Role.USER));
            user.setStates(Collections.singleton(State.ACTIVE));
            userRepository.save(user);
            return ResponseEntity.ok("Registration user with email " + user.getEmail() + " successful!");
        } else {
            return ResponseEntity.badRequest().body("User with this email: " + user.getEmail()+ " already exists!");
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity update(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            User updateUser = userRepository.findByEmail(user.getEmail()).get();
            user.setId(updateUser.getId());
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            user.setRoles(Collections.singleton(Role.USER));
            user.setStates(Collections.singleton(State.ACTIVE));
            userRepository.save(user);
            return ResponseEntity.ok("Data for user with email " + user.getEmail() + " was refreshing!");
        } else return ResponseEntity.badRequest().body("User with this email: " + user.getEmail()+ " not found");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity updateById(Long id, User user) {
        if (userRepository.findById(id).isPresent()) {
            user.setId(id);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(Role.USER));
            user.setStates(Collections.singleton(State.ACTIVE));
            userRepository.save(user);
            return ResponseEntity.ok("Your data was refreshing!");
        } else return ResponseEntity.badRequest().body("User with this email: " + user.getEmail()+ " not found");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity delete(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            //user.setStates(Collections.singleton(State.DELETE));
            userRepository.deleteByEmail(user.getEmail());
            return ResponseEntity.ok("User with email "+ user.getEmail() + " was delete.");
        }
        else return ResponseEntity.badRequest().body("User with this email: " + user.getEmail()+ " not found");
    }

}
