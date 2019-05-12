package WebApp.repository.specifications;

import WebApp.entity.AbstractEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

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
                        builder.lower(path),
                        builder.lower(builder.literal("%" + criteria.getValue() + "%"))
                );
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
