package WebApp.service;

import WebApp.entity.Role;
import WebApp.entity.State;
import WebApp.entity.User;
import WebApp.repository.UserRepository;
import WebApp.repository.specifications.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

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
    public ResponseEntity<Iterable<User>> getAll() {
        return ResponseEntity.ok(userRepository.findByStates(State.ACTIVE));
    }


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
        } else return ResponseEntity.badRequest().body("User with email: " + user.getEmail()+ " already exists!");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity updateById(Long id, User user) {
        if (id == null){
            id = userRepository.findByEmail(user.getEmail()).get().getId();
        }
        if (id != null && userRepository.findById(id).isPresent()) {
            user.setId(id);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(Role.USER));
            user.setStates(Collections.singleton(State.ACTIVE));
            userRepository.save(user);
            return ResponseEntity.ok("Data for user with email " + user.getEmail() + " was refreshing!");
        } else return ResponseEntity.badRequest().body("User with this email: " + user.getEmail()+ " not found");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity update(User user) {
        return updateById(user.getId(),user);
    }




    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity delete(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            user.setStates(Collections.singleton(State.DELETE));
            userRepository.save(user);
            //userRepository.deleteByEmail(user.getEmail());
            return ResponseEntity.ok("User with email "+ user.getEmail() + " was delete.");
        } else return ResponseEntity.badRequest().body("User with email: " + user.getEmail()+ " not found");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity deleteById(Long id) {
        Optional<User> optionalDeleteUser = userRepository.findById(id);
        if(optionalDeleteUser.isPresent()){
            User deleteUser = optionalDeleteUser.get();
            deleteUser.setStates(Collections.singleton(State.DELETE));
            userRepository.save(deleteUser);
            //userRepository.deleteByEmail(user.getEmail());
            return ResponseEntity.ok("User with email " + deleteUser.getEmail() + " was delete.");
        } else return ResponseEntity.badRequest().body("User with email: " + optionalDeleteUser.get().getEmail()+ " not found");
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Iterable<User>> bySpec (UserSpecification userSpecification){
        return ResponseEntity.ok(userRepository.findAll(Specification.where(userSpecification)));
    }

}
