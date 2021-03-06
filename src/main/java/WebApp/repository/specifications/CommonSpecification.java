package WebApp.repository.specifications;

import WebApp.entity.AbstractEntity;
import WebApp.entity.enums.Category;
import WebApp.entity.enums.ReservationStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Arrays;

public class CommonSpecification<E extends AbstractEntity> implements Specification<E> {

    private SearchCriteria criteria;

    public CommonSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        Path path = getPath(root, criteria.getKey());

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThan(
                    path, criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThan(
                    path, criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (path.getJavaType() == String.class) {
                return builder.like(
                        path,
                        "%" + criteria.getValue() + "%"
                );
            }
            if (path.getJavaType() == ReservationStatus.class) {
                return builder.equal(path, ReservationStatus.get(criteria.getValue().toString()));
            }
            if (path.getJavaType() == Boolean.class) {
                boolean search = false;
                if (criteria.getValue().toString().toLowerCase().equals("true")) {
                    search = true;
                }
                return builder.equal(path, search);
            }
            if (path.getJavaType() == Category.class) {
                return builder.equal(path, Category.get(criteria.getValue().toString()));
            } else {
                return builder.equal(path, criteria.getValue());
            }


        }
        return null;
    }

    private Path getPath(Path path, String key) {
        String[] fieldNames = key.split("\\.");
        for (String field : fieldNames) {
            path = path.get(field);
        }
        return path;
    }

}
