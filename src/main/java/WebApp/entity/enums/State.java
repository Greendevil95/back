package WebApp.entity.enums;

public enum State {
    ACTIVE,
    BANNED,
    DELETE;

    public static State get(String state) {
        switch (state.toLowerCase()) {
            case ("active"):
                return ACTIVE;
            case ("banned"):
                return BANNED;
            case ("delete"):
                return DELETE;
            default:
                return null;
        }
    }
    }
