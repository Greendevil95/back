package WebApp.service;

import WebApp.entity.AbstractEntity;
import WebApp.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>> implements CommonService<E> {

    protected final R repository;

    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }

    private static int pageSize = 3;

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<E> getById(Long id) {
        if (repository.findById(id).isPresent()) {
            E entity = repository.findById(id).get();
            return ResponseEntity.ok(entity);
        } else return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Iterable<E>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<E>> getByPage(Integer numPage, String fieldForSort){

        if (fieldForSort == null) {
            fieldForSort = "id";
        }
        if (numPage == null){
            return ResponseEntity.ok((List)repository.findAll());
        }
        else {
            Pageable pageable = PageRequest.of(numPage, pageSize, Sort.by(fieldForSort));
            return ResponseEntity.ok(repository.findAll(pageable).getContent());
        }
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
