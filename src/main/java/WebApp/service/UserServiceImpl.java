package WebApp.service;

import WebApp.entity.Role;
import WebApp.entity.State;
import WebApp.entity.User;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            user.setRating(0);
            user.setRoles(Collections.singleton(Role.USER));
            user.setStates(Collections.singleton(State.ACTIVE));
            userRepository.save(user);
            return ResponseEntity.ok("Registration user with email " + user.getEmail() + " successful!");
        } else return ResponseEntity.badRequest().body("User with email: " + user.getEmail()+ " already exists!");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity updateById(Long id, User user) {
        if (id == null){
            id = userRepository.findByEmail(user.getEmail()).get().getId();
        }
        Optional<User> updateUser = userRepository.findById(id);
        if (!updateUser.isPresent()) {
            return ResponseEntity.badRequest().body("User with this email: " + user.getEmail()+ " not found");
        }
        if (!isAuthUser(updateUser.get())){
            ResponseEntity.badRequest().body("Its not you account.");
        }

        user.setId(updateUser.get().getId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Data for user with email " + user.getEmail() + " was refreshing!");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity update(User user) {
        return updateById(user.getId(),user);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity delete(User user){
        Optional<User> deleteUser = userRepository.findByEmail(user.getEmail());
        if (deleteUser.isPresent()) {
            return deleteById(deleteUser.get().getId());
        } else return ResponseEntity.badRequest().body("User with email: " + deleteUser.get().getEmail()+ " not found");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity deleteById(Long id) {
        Optional<User> deleteUser = userRepository.findById(id);
        if(!deleteUser.isPresent()) {
            return ResponseEntity.badRequest().body("User with email: " + deleteUser.get().getEmail()+ " not found");
        }
        if (!isAuthUser(deleteUser.get())){
            return ResponseEntity.badRequest().body("Its not you account.");
        }
        userRepository.deleteById(id);

        deleteUser.get().setStates(Collections.singleton(State.DELETE));
        deleteUser.get().setOrganization(null);
        deleteUser.get().setReservations(null);
        userRepository.save(deleteUser.get());
        return ResponseEntity.ok("User with email " + deleteUser.get().getEmail() + " was delete.");
    }

//    @PreAuthorize("hasAuthority('USER')")
//    public ResponseEntity<Iterable<User>> bySpec (UserSpecification userSpecification){
//        return ResponseEntity.ok(userRepository.findAll(Specification.where(userSpecification)));
//    }

}
