//package WebApp.repository.specifications;
//
//public class test<T> extends PathSpecification<T> {
//
//    private static final long serialVersionUID = 1L;
//
//    protected String[] searchedNames;       //массив строк , содержит скорее всего путь
//
//    public test(QueryContext queryContext, String path, String... args) {
//        super(queryContext, path);
//        if (args == null || args.length == 0) {
//            throw new IllegalArgumentException("Expected at least one argument (the enum constant name to match against)");
//        } else {
//            this.searchedNames = args;
//        }
//    }
//
//    @Override
//    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//        List<Enum<?>> matchingEnumConstants = findMatchingEnumConstants(getEnumClass(root));
//        Iterator<Enum<?>> iterator = matchingEnumConstants.iterator();
//        Predicate combinedPredicates = builder.equal(this.<Enum<?>> path(root), iterator.next());
//        while (iterator.hasNext()) {
//            combinedPredicates = builder.or(builder.equal(this.<Enum<?>> path(root), iterator.next()), combinedPredicates);
//        }
//        return combinedPredicates;
//    }
//
//    private Class<? extends Enum<?>> getEnumClass(Root<T> root) {
//        Class<? extends Enum<?>> enumClass = this.<Enum<?>> path(root).getJavaType();
//        if (!enumClass.isEnum()) {
//            throw new IllegalArgumentException("Type of field with path " + super.path + " is not enum!");
//        }
//        return enumClass;
//    }
//
//    private List<Enum<?>> findMatchingEnumConstants(Class<? extends Enum<?>> enumClass) {
//        List<String> searchedNamesCopy = new ArrayList<>(Arrays.asList(searchedNames));  //searchedNamesCopy массив содержит элементы пути
//        List<Enum<?>> matchingEnumConstants = new ArrayList<>();                          // matchingEnumConstants массив из енума
//        Enum<?>[] enumConstants = enumClass.getEnumConstants();                           // получает полностью заполненный массив констант.
//        for (Enum<?> enumConstant : enumConstants) {
//            Iterator<String> i = searchedNamesCopy.iterator();
//            while (i.hasNext()) {
//                if (enumConstant.name().equals(i.next())) {
//                    matchingEnumConstants.add(enumConstant);
//                    i.remove();
//                }
//            }
//        }
//        if (searchedNamesCopy.size() > 0) {
//            throw new IllegalArgumentException("The following enum constants do not exists: " + StringUtils.join(searchedNamesCopy, ", "));
//        }
//        return matchingEnumConstants;
//    }
//
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = super.hashCode();
//        result = prime * result + Arrays.hashCode(searchedNames);
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (!super.equals(obj))
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        EqualEnum<?> other = (EqualEnum<?>) obj;
//        if (!Arrays.equals(searchedNames, other.searchedNames))
//            return false;
//        return true;
//    }
//
//
//}
