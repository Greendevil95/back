package WebApp.service;

import WebApp.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommonService<E extends AbstractEntity> {

    ResponseEntity add(E entity);

    ResponseEntity<E> getById(Long Id);

    ResponseEntity<Iterable<E>> getAll();

    ResponseEntity update(E entity);

    ResponseEntity updateById(Long id, E entity);

    ResponseEntity delete(E entity);

    ResponseEntity deleteById(Long Id);

    ResponseEntity<List<E>> getByPage(Integer numPage, String fieldForSort);
}
