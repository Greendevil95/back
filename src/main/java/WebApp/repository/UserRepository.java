package WebApp.repository;

import WebApp.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User,Long> {
    //List<User> findByEmail(String email);
    List<User> findByLogin(String login);
    void deleteById(Long id);
    User findById(Integer id);
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);

}
