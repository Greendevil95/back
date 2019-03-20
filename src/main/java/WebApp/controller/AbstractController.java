package WebApp.controller;

import WebApp.entity.AbstractEntity;
import WebApp.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class AbstractController <E extends AbstractEntity, S extends CommonService<E>>{

    private final S service;

    @Autowired
    public AbstractController(S service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity add(@RequestBody E entity) {
        return service.add(entity);
    }

    @GetMapping()
    public ResponseEntity<Iterable<E>> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<E> getById(@PathVariable(value = "id") Long id) {
        return service.getById(id);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody E entity) {
        return service.update(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable(value = "id") Long id,@RequestBody E entity) {
        return service.updateById(id,entity);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody E entity) {
        return service.delete(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable(value = "id") Long id) {
        return service.deleteById(id);
    }
}
