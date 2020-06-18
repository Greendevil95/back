package WebApp.entity.enums;

public enum Event {
    VIEW,
    TRANSACTION,
    RATING;

    public static Event get(String ivent) {

        switch (ivent.toLowerCase()) {
            case ("view"):
                return VIEW;
            case ("transaction"):
                return TRANSACTION;
            case ("rating"):
                return RATING;
            default:
                return null;
        }
    }
}




