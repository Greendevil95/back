package WebApp.entity.enums;

public enum ReservationStatus {
    INPROCESS,
    ASSEPTED,
    FINISHED,
    CUSTOMERREJECT,
    OWNERREJECT;

    public static ReservationStatus get(String status){
        switch (status.toLowerCase()){
            case ("inprocess") :
                return INPROCESS;
            case ("assepted") :
                return ASSEPTED;
            case ("finished") :
                return FINISHED;
            case ("customerreject") :
                return CUSTOMERREJECT;
            case ("ownerreject") :
                return OWNERREJECT;
            default:
                return null;
        }
    }
}
