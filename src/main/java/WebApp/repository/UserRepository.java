package WebApp.repository;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository extends CommonRepository<User> {
    void deleteById(Long id);
    Optional<User> findByEmail(String email);
    Page<User> findByOrganization(Organization organization, Pageable pageable);
    Page<User> findByReservations(Reservation reservation, Pageable pageable);
}
