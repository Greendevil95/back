package WebApp.repository;

import WebApp.entity.User;
import java.util.Optional;

public interface UserRepository extends CommonRepository<User> {
    void deleteById(Long id);
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
}
