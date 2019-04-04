package WebApp.repository;

import WebApp.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@NoRepositoryBean
public interface CommonRepository<E extends AbstractEntity> extends PagingAndSortingRepository<E,Long>, JpaSpecificationExecutor<E> {
    List<E> findAll();
}
