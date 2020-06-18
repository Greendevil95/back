package WebApp.entity.response;

import WebApp.entity.AbstractEntity;
import org.springframework.data.domain.Page;

public class EntityResponse<E extends AbstractEntity> {
    private Integer totalPages;
    private Integer size;
    private Integer number;
    private Long totalElements;
    private Integer numberOfElements;
    private Iterable<E> content;

    public EntityResponse(Page<E> page) {
        totalPages = page.getTotalPages();
        size = page.getSize();
        number = page.getNumber();
        totalElements = page.getTotalElements();
        numberOfElements = page.getNumberOfElements();
        content = page.getContent();
    }

    public EntityResponse() {
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Iterable<E> getContent() {
        return content;
    }

    public void setContent(Iterable<E> content) {
        this.content = content;
    }
}
