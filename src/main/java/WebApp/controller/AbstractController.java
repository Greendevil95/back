package WebApp.controller;

import WebApp.entity.AbstractEntity;
import WebApp.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public class AbstractController <E extends AbstractEntity, S extends CommonService<E>> implements CommonController<E> {

    private final S service;

    @Autowired
    public AbstractController(S service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Iterable<E>> getAll() {
        return service.getAll();
    }

    @Override
    public ResponseEntity<E> getById(@PathVariable(value = "id") Long id) {
        return service.getById(id);
    }

    @Override
    public ResponseEntity update(@RequestBody E entity) {
        return service.update(entity);
    }

    @Override
    public ResponseEntity updateById(@PathVariable(value = "id") Long id,@RequestBody E entity) {
        return service.updateById(id,entity);
    }

    @Override
    public ResponseEntity delete(@RequestBody E entity) {
        return service.delete(entity);
    }

    @Override
    public ResponseEntity deleteById(@PathVariable(value = "id") Long id) {
        return service.deleteById(id);
    }
}
