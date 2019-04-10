package WebApp.service;

import WebApp.entity.AbstractEntity;
import WebApp.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>> implements CommonService<E> {

    protected final R repository;

    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }

    private static int pageSize = 1 ;

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
    public ResponseEntity<Iterable<E>> getAll(Integer page, String fieldForSort) {
        if (page == null){
            page = 0;
        }
        if (fieldForSort == null) {
            fieldForSort = "id";
        }
        Pageable pageable = PageRequest.of(page, pageSize,Sort.by(fieldForSort));
        return ResponseEntity.ok(repository.findAll(pageable).getContent());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity deleteById(Long id) {
        if(repository.findById(id).isPresent()){
            repository.deleteById(id);
            return ResponseEntity.ok("Object with id " + id + " was delete.");
        }
        else return ResponseEntity.notFound().build();
    }
}
