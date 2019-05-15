package WebApp.repository.specifications;

import WebApp.entity.AbstractEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommonSpecificationBuilder<E extends AbstractEntity> {

    private final List<SearchCriteria> params;

    public CommonSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public CommonSpecificationBuilder with(String connect, String key, String operation, Object value) {
        params.add(new SearchCriteria(connect, key, operation, value));
        return this;
    }

    public Specification<E> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(CommonSpecification<E>::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            if (params.get(i).getConnect().equals("and")) {
                result = Specification.where(result)
                        .and(specs.get(i));
            }else {
                result = Specification.where(result)
                        .or(specs.get(i));
            }
        }
        return result;
    }
}
