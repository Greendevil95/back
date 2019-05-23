package WebApp.service;

import WebApp.entity.AbstractEntity;
import WebApp.entity.User;
import WebApp.entity.enums.Category;
import WebApp.entity.response.EntityResponse;
import WebApp.repository.CommonRepository;
import WebApp.repository.UserRepository;
import WebApp.repository.specifications.CommonSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>> implements CommonService<E> {

    private static int constPageSize = 10;
    protected final R repository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }

    public static int getPageSize() {
        return constPageSize;
    }

    public static void setPageSize(int pageSize) {
        AbstractService.constPageSize = pageSize;
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<E> getById(Long id) {
        if (repository.findById(id).isPresent()) {
            E entity = repository.findById(id).get();
            return ResponseEntity.ok(entity);
        } else return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<EntityResponse<E>> getAll(Integer page, Integer pageSize, String fieldForSort, String search) {

        Specification<E> specification = initSpecification(search);
        Pageable pageable = initPageable(page, fieldForSort, pageSize);

        return ResponseEntity.ok(new EntityResponse<E>(repository.findAll(specification, pageable)));
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity deleteById(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok("Object with id " + id + " was delete.");
        } else return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('USER')")
    public static ResponseEntity getAllCategory() {
        Set<Category> categories = new HashSet<>();
        categories.add(Category.СПОРТ);
        categories.add(Category.ЗДОРОВЬЕ);
        categories.add(Category.КРАСОТА);
        categories.add(Category.ОБУЧЕНИЕ);
        categories.add(Category.КУЛИНАРИЯ);
        categories.add(Category.РАЗВЛЕЧЕНИЯ);
        categories.add(Category.РАЗНОЕ);
        return ResponseEntity.ok(categories);
    }


    public boolean isAuthUser(User user) {
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return (user.equals(userRepository.findByEmail(authUserName).get()));
    }

    protected Specification<E> initSpecification(String search) {
        CommonSpecificationBuilder<E> builder = new CommonSpecificationBuilder();
        Pattern pattern = Pattern.compile("(and|or)(\\w|.+?)(:|<|>)(?U)(\\w+?),");

        Matcher matcher = pattern.matcher("and" + search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
        }
        Specification<E> specification = builder.build();
        return specification;
    }

    protected Pageable initPageable(Integer page, String fieldForSort, Integer pageSize) {
        if (page == null) {
            page = 0;
        }
        if (fieldForSort == null) {
            fieldForSort = "id.ask";
        }
        if (pageSize == null) {
            pageSize = constPageSize;
        }
        String[] properties = fieldForSort.split("\\.");
        if (properties.length==1){
            return PageRequest.of(page, pageSize, Sort.Direction.ASC, properties[0]);
        }
        if (properties[1].toLowerCase().equals("desc")) {
            return PageRequest.of(page, pageSize, Sort.Direction.DESC, properties[0]);
        } else {
            return PageRequest.of(page, pageSize, Sort.Direction.ASC, properties[0]);
        }
    }
}
