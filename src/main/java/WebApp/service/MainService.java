package WebApp.service;


import WebApp.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface MainService {
    Iterable<User> getAll();
    User getById(Integer id);
    void save(User user);
    void deleteById(long id);
}
