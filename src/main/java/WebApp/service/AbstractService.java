package WebApp.service;

import WebApp.entity.AbstractEntity;
import WebApp.entity.User;
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

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E> > implements CommonService<E> {

    protected final R repository;

    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }

    private static int pageSize = 10 ;

    public static void setPageSize(int pageSize) {
        AbstractService.pageSize = pageSize;
    }

    public static int getPageSize() {
        return pageSize;
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<E> getById(Long id) {
        if (repository.findById(id).isPresent()) {
            E entity = repository.findById(id).get();
            return ResponseEntity.ok(entity);
        } else return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<EntityResponse<E>> getAll(Integer page, String fieldForSort, String search) {

        Specification<E> specification = initSpecification(search);
        Pageable pageable = initPageable(page,fieldForSort,pageSize);

        return ResponseEntity.ok(new EntityResponse<E>(repository.findAll(specification, pageable)));
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity deleteById(Long id) {
        if(repository.findById(id).isPresent()){
            repository.deleteById(id);
            return ResponseEntity.ok("Object with id " + id + " was delete.");
        }
        else return ResponseEntity.notFound().build();
    }

    @Autowired
    UserRepository userRepository;

    public boolean isAuthUser(User user){
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return (user.equals(userRepository.findByEmail(authUserName).get()));
    }

    protected Specification<E> initSpecification(String search){
        CommonSpecificationBuilder<E> builder = new CommonSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w|.+?)(:|<|>)(?U)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<E> specification =  builder.build();
        return  specification;
    }

    protected Pageable initPageable(Integer page, String fieldForSort, int pageSize){
        if (page == null){
            page = 0;
        }
        if (fieldForSort == null) {
            fieldForSort = "id.ask";
        }
        String[] properties = fieldForSort.split("\\.");

        if (properties[1].toLowerCase().equals("desc")) {
            return PageRequest.of(page, pageSize, Sort.Direction.DESC, properties[0]);
        }
        else{
            return PageRequest.of(page, pageSize, Sort.Direction.ASC, properties[0]);
        }
    }
}
