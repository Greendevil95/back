package WebApp.entity;

public enum ReservationStatus {
    INPROCESS,
    ASSEPTED,
    CUSTOMERREJECT,
    OWNERREJECT;

    public static ReservationStatus get(String status){
        switch (status.toLowerCase()){
            case ("inprocess") :
                return INPROCESS;
            case ("assepted") :
                return ASSEPTED;
            case ("customerreject") :
                return CUSTOMERREJECT;
            case ("ownerreject") :
                return OWNERREJECT;
            default:
                return null;
        }
    }
}
