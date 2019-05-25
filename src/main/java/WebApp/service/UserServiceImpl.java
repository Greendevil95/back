package WebApp.service;

import WebApp.entity.Interest;
import WebApp.entity.Reservation;
import WebApp.entity.User;
import WebApp.entity.enums.ReservationStatus;
import WebApp.entity.enums.Role;
import WebApp.entity.enums.State;
import WebApp.repository.InterestRepository;
import WebApp.repository.ReservationRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl extends AbstractService<User, UserRepository> implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getAuthUser() {
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        return ResponseEntity.ok(authUser);
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getAuthUserId() {
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        return ResponseEntity.ok(authUser.getId());
    }

    @PreAuthorize("hasAuthority('USER')")
    public Iterable<Interest> getInterestsForUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return null;
        }
        return interestRepository.findByUserOrderByCountDesc(user.get());
    }

    @PreAuthorize("hasAuthority('USER')")
    public Iterable<Interest> get3InterestsForUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return null;
        }
        return interestRepository.findFirst3ByUserOrderByCountDesc(user.get());
    }

    @Override
    public ResponseEntity add(User user) {
        if (!userRepository.findByEmail(user.getEmail()).isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));


            user.setRoles(Collections.singleton(Role.USER));
            if (user.getEmail().equals("denisadmin@mail.ru")) {
                Set<Role> roles = new HashSet<>();
                roles.add(Role.USER);
                roles.add(Role.ADMIN);
                user.setRoles(roles);
            }
            user.setStates(State.ACTIVE);
            user.setVip(false);
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
        user.setVip(updateUser.get().getVip());
        user.setReservations(updateUser.get().getReservations());
        user.setOrganization(updateUser.get().getOrganization());
        user.setStates(updateUser.get().getStates());
        user.setRoles(updateUser.get().getRoles());
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

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity setVipForAuthUser() {
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();

        authUser.setVip(true);
        userRepository.save(authUser);
        return ResponseEntity.ok("User with id " + authUser.getId() + " is vip. ");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity setState(Long id, String state) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("User with id " + id + " not found.");
        }
        user.get().setStates(State.get(state));

        if (state.toLowerCase().equals("banned")) {
            Iterable<Reservation> reservations = reservationRepository.findByUser(user.get());
            for (Reservation r : reservations) {
                if (ReservationStatus.canChange(r.getStatus(), ReservationStatus.OWNERREJECT)) {
                    r.setStatus(ReservationStatus.OWNERREJECT);
                    reservationRepository.save(r);
                }
            }
        }
        userRepository.save(user.get());
        return ResponseEntity.ok("User with id " + id + " changed state. ");
    }


}
