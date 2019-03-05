package WebApp.controller;

import WebApp.entity.AbstractEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface CommonController<E extends AbstractEntity> {

    @GetMapping("")
    ResponseEntity<Iterable<E>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<E> getById(@PathVariable(value = "id") Long id);

    @PutMapping
    ResponseEntity update(@RequestBody E entity);

    @PutMapping("/{id}")
    ResponseEntity updateById(@PathVariable(value = "id") Long id, @RequestBody E entity);

    @DeleteMapping
    ResponseEntity delete(@RequestBody E entity);

    @DeleteMapping("/{id}")
    ResponseEntity deleteById(@PathVariable(value = "id") Long id);



}
