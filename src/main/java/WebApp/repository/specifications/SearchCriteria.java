package WebApp.repository.specifications;

public class SearchCriteria {
    private String connect;
    private String key;
    private String operation;
    private Object value;


    public SearchCriteria(String connect, String key, String operation, Object value) {
        this.connect = connect;
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "connect='" + connect + '\'' +
                ", key='" + key + '\'' +
                ", operation='" + operation + '\'' +
                ", value=" + value +
                '}';
    }
}


