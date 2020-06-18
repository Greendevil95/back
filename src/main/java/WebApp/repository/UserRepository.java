package WebApp.repository;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CommonRepository<User> {
    void deleteById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByOrganization(Organization organization);

    Optional<User> findByReservations(Reservation reservation);

}
