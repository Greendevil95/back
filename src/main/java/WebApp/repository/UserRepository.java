package WebApp.repository;

import WebApp.entity.Organization;
import WebApp.entity.State;
import WebApp.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CommonRepository<User> {
    void deleteById(Long id);
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
    Optional<User> findByOrganization (Organization organization);
    List<User> findByStates(State state, Pageable pageable);
}
