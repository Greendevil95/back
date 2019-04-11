//package WebApp.repository.specifications;
//
//import WebApp.entity.AbstractEntity;
//import WebApp.entity.User;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class CommonSpecificationBuilder<E extends AbstractEntity, T extends CommonSpecification<E>> {
//
//    private final List<SearchCriteria> params;
//
//    public CommonSpecificationBuilder() {
//        params = new ArrayList<SearchCriteria>();
//    }
//
//    public CommonSpecificationBuilder with(String key, String operation, Object value) {
//        params.add(new SearchCriteria(key, operation, value));
//        return this;
//    }
//
//    public Specification<E> build() {
//        if (params.size() == 0) {
//            return null;
//        }
//
//        List<Specification> specs = params.stream()
//                .map(T::new)
//                .collect(Collectors.toList());
//
//        Specification result = specs.get(0);
//
//        for (int i = 1; i < params.size(); i++) {
//            result = Specification.where(result)
//                    .and(specs.get(i));
//        }
//        return result;
//    }
//}
