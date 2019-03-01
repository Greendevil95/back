package WebApp.repository;

import WebApp.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    void deleteById(Long id);
    User findById(Integer id);
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);

}
