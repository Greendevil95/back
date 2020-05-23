package WebApp.entity.enums;

public enum  ReservationStatus {
    INPROCESS,
    ACCEPTED,
    FINISHED,
    CUSTOMERREJECT,
    OWNERREJECT;

    public static ReservationStatus get(String status) {
        switch (status.toLowerCase()) {
            case ("inprocess"):
                return INPROCESS;
            case ("accepted"):
                return ACCEPTED;
            case ("finished"):
                return FINISHED;
            case ("customerreject"):
                return CUSTOMERREJECT;
            case ("ownerreject"):
                return OWNERREJECT;
            default:
                return null;
        }
    }

    public static String getRus(String status) {
        switch (status.toLowerCase()) {
            case ("inprocess"):
                return "в процессе";
            case ("accepted"):
                return "подтверждена";
            case ("finished"):
                return "закончена";
            case ("customerreject"):
                return "отменил заказчик";
            case ("ownerreject"):
                return "отменил мастер";
            default:
                return null;
        }

    }

    public static boolean canChange(ReservationStatus oldStatus, ReservationStatus newStatus) {
        switch (oldStatus) {
            case INPROCESS:
                return true;
            case ACCEPTED:
                return newStatus != INPROCESS;
            case OWNERREJECT:
                return false;
            case CUSTOMERREJECT:
                return false;
            case FINISHED:
                return false;
            default:
                return false;
        }
    }
}
