package WebApp.service;

import WebApp.entity.AbstractEntity;
import org.springframework.http.ResponseEntity;

public interface CommonService<E extends AbstractEntity> {

    ResponseEntity add(E entity);

    ResponseEntity<E> getById(Long Id);

    ResponseEntity<Iterable<E>> getAll(Integer page, String fieldForSort);

    ResponseEntity update(E entity);

    ResponseEntity updateById(Long id, E entity);

    ResponseEntity delete(E entity);

    ResponseEntity deleteById(Long Id);

}
