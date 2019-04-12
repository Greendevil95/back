package WebApp.controller;

import WebApp.entity.AbstractEntity;
import WebApp.entity.User;
import WebApp.entity.response.EntityResponse;
import WebApp.repository.specifications.CommonSpecification;
import WebApp.repository.specifications.CommonSpecificationBuilder;
import WebApp.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public ResponseEntity<EntityResponse<E>> getAll(@RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "field" , required = false) String fieldForSort,
                                                    @RequestParam(value = "search", required = false) String search) {
//        CommonSpecificationBuilder<E,CommonSpecification<E>> builder = new CommonSpecificationBuilder();
//        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
//        Matcher matcher = pattern.matcher(search + ",");
//        while (matcher.find()) {
//            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
//        }
//
//        CommonSpecification<E> spec = (CommonSpecification<E>) builder.build();
        return service.getAll(page,fieldForSort);
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
