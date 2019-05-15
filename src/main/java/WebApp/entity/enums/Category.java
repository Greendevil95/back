package WebApp.entity.enums;

public enum Category {
    SPORT,
    HEALTHANDBEAUTY,
    TRAINING,
    COOKING,
    ENTERTAINMENT,
    DIFFERENT;

    public static Category get(String interest) {
        switch (interest.toLowerCase()) {
            case ("sport"):
                return SPORT;
            case ("healthandbeauty"):
                return HEALTHANDBEAUTY;
            case ("training"):
                return TRAINING;
            case ("cooking"):
                return COOKING;
            case ("entertainment"):
                return ENTERTAINMENT;
            case "different":
                return DIFFERENT;
            default:
                return null;
        }
    }

    public static String get(Category category) {
        switch (category) {
            case SPORT:
                return "sport";
            case HEALTHANDBEAUTY:
                return "healthandbeauty";
            case TRAINING:
                return "training";
            case COOKING:
                return "cooking";
            case ENTERTAINMENT:
                return "entertainment";
            case DIFFERENT:
                return "different";
            default:
                return null;
        }
    }
}
