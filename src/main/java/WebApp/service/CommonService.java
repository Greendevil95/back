package WebApp.service;

import WebApp.entity.AbstractEntity;
import WebApp.entity.response.EntityResponse;
import WebApp.repository.specifications.CommonSpecification;
import org.springframework.http.ResponseEntity;

public interface CommonService<E extends AbstractEntity> {

    ResponseEntity add(E entity);

    ResponseEntity<E> getById(Long Id);

    ResponseEntity<EntityResponse<E>> getAll(Integer page, String fieldForSort);

    ResponseEntity update(E entity);

    ResponseEntity updateById(Long id, E entity);

    ResponseEntity delete(E entity);

    ResponseEntity deleteById(Long Id);

}
