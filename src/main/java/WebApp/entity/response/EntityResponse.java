package WebApp.entity.response;

import WebApp.entity.AbstractEntity;
import org.springframework.data.domain.Page;

public class EntityResponse<E extends AbstractEntity> {
    private Integer totalPages;
    private Integer size;
    private Integer number;
    private Iterable<E> entity;

    public EntityResponse(Page<E> page) {
        totalPages = page.getTotalPages();
        size = page.getSize();
        number = page.getNumber();
        entity = page.getContent();
    }

    public EntityResponse() {
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setEntity(Iterable<E> entity) {
        this.entity = entity;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getNumber() {
        return number;
    }

    public Iterable<E> getEntity() {
        return entity;
    }
}
