package WebApp.service.forStatistics;

import WebApp.entity.Organization;

public class OrganizationStatistics {
    private Organization organization;
    private Integer countSuccsesReservation;
    private Float rating;

    public OrganizationStatistics(Organization organization) {
        this.organization = organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void setCountSuccsesReservation(Integer countSuccsesReservation) {
        this.countSuccsesReservation = countSuccsesReservation;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Integer getCountSuccsesReservation() {
        return countSuccsesReservation;
    }

    public Float getRating() {
        return rating;
    }
}
