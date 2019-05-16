package WebApp.repository.specifications;

import WebApp.entity.AbstractEntity;
import WebApp.entity.enums.State;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
            System.out.println(path.getJavaType());
            if (path.getJavaType() == String.class) {
                return builder.like(
                        builder.lower(path),
                        builder.lower(builder.literal("%" + criteria.getValue() + "%"))
                );
            }else{
                return builder.equal(path,criteria.getValue());
            }
//            if (path.getJavaType() == Set.class)
//            {
//                return builder.equal(path, State.DELETE);
//            }

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
