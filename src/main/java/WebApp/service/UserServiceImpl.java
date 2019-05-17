package WebApp.service;

import WebApp.entity.Reservation;
import WebApp.entity.User;
import WebApp.entity.enums.Category;
import WebApp.entity.enums.Role;
import WebApp.entity.enums.State;
import WebApp.repository.OrganizationRepository;
import WebApp.repository.ReservationRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractService<User, UserRepository> implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity getAuthUser() {
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        return ResponseEntity.ok(authUser);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity getAuthUserId() {
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        return ResponseEntity.ok(authUser.getId());
    }

    @Override
    public Map<Category, Integer> getInterestsForUserById(Long id) {
        User user = userRepository.findById(id).get();
        Iterable<Reservation> reservations = reservationRepository.findByUser(user);

        Map<Category, Integer> integerMap = new LinkedHashMap<Category, Integer>();
        for (Reservation r : reservations) {

            Category category = r.getService().getCategory();
            if (integerMap.containsKey(category)) {
                integerMap.put(category, integerMap.get(category) + 1);
            } else {
                integerMap.put(category, 1);
            }
        }

        Map<Category, Integer> result = new LinkedHashMap<Category, Integer>();

        integerMap.entrySet().stream()
                .sorted(Map.Entry.<Category, Integer>comparingByValue().reversed())
                .forEach(x->result.put(x.getKey(),x.getValue()));

        return result;
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
        } else return ResponseEntity.badRequest().body("User with email: " + user.getEmail() + " already exists!");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity updateById(Long id, User user) {
        if (id == null) {
//            String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
//            id = userRepository.findByEmail(authUserName).get().getId();
            return ResponseEntity.badRequest().body("Id is null.");
        }
        Optional<User> updateUser = userRepository.findById(id);
        if (!updateUser.isPresent()) {
            return ResponseEntity.badRequest().body("User with id " + id + " not found");
        }
        if (!isAuthUser(updateUser.get())) {
            ResponseEntity.badRequest().body("Its not you account.");
        }

        user.setId(updateUser.get().getId());
        if (user.getEmail() == null) {
            user.setEmail(updateUser.get().getEmail());
        }

        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(updateUser.get().getPassword());
        }
        user.setReservations(updateUser.get().getReservations());
        user.setOrganization(updateUser.get().getOrganization());
        user.setStates(updateUser.get().getStates());
        user.setRoles(updateUser.get().getRoles());
        user.setRating(updateUser.get().getRating());
        userRepository.save(user);
        return ResponseEntity.ok("Data for user with email " + user.getEmail() + " was refreshing!");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity update(User user) {
        return updateById(user.getId(), user);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity delete(User user) {
        Optional<User> deleteUser = userRepository.findByEmail(user.getEmail());
        if (deleteUser.isPresent()) {
            return deleteById(deleteUser.get().getId());
        } else
            return ResponseEntity.badRequest().body("User with email: " + deleteUser.get().getEmail() + " not found");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity deleteById(Long id) {
        Optional<User> deleteUser = userRepository.findById(id);
        if (!deleteUser.isPresent()) {
            return ResponseEntity.badRequest().body("User with email: " + deleteUser.get().getEmail() + " not found");
        }
        if (!isAuthUser(deleteUser.get())) {
            return ResponseEntity.badRequest().body("Its not you account.");
        }
        userRepository.deleteById(id);

//        deleteUser.get().setStates(Collections.singleton(State.DELETE));
//        deleteUser.get().setOrganization(null);
//        deleteUser.get().setReservations(null);
//        userRepository.save(deleteUser.get());
        return ResponseEntity.ok("User with email " + deleteUser.get().getEmail() + " was delete.");
    }

}
