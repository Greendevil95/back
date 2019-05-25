package WebApp.repository;

import WebApp.entity.Interest;
import WebApp.entity.User;
import WebApp.entity.enums.Category;

import java.util.Optional;

public interface InterestRepository extends CommonRepository<Interest> {
    Optional<Interest> findByUserAndCategory(User user, Category category);

    Iterable<Interest> findFirst3ByUserOrderByCountDesc(User user);

    Iterable<Interest> findByUserOrderByCountDesc(User user);
}
