package WebApp.entity.enums;

public enum Category {
    СПОРТ,
    ЗДОРОВЬЕ,
    КРАСОТА,
    ОБУЧЕНИЕ,
    КУЛИНАРИЯ,
    РАЗВЛЕЧЕНИЯ,
    РАЗНОЕ;

    public static Category get(String interest) {
        switch (interest.toLowerCase()) {
            case ("спорт"):
                return СПОРТ;
            case ("здоровье"):
                return ЗДОРОВЬЕ;
            case ("красота"):
                return КРАСОТА;
            case ("обучение"):
                return ОБУЧЕНИЕ;
            case ("кулинария"):
                return КУЛИНАРИЯ;
            case ("развлечения"):
                return РАЗВЛЕЧЕНИЯ;
            case "разное":
                return РАЗНОЕ;
            default:
                return null;
        }
    }

    public static String get(Category category) {
        switch (category) {
            case СПОРТ:
                return "спорт";
            case ЗДОРОВЬЕ:
                return "здоровье";
            case КРАСОТА:
                return "красота";
            case ОБУЧЕНИЕ:
                return "обучение";
            case КУЛИНАРИЯ:
                return "кулинария";
            case РАЗВЛЕЧЕНИЯ:
                return "развлечения";
            case РАЗНОЕ:
                return "разное";
            default:
                return null;
        }
    }


    @Override
    public String toString() {
        return get(this);
    }
}
