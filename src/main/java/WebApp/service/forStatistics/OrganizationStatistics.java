package WebApp.service.forStatistics;

import WebApp.entity.Organization;

public class OrganizationStatistics {
    private Organization organization;
    private Integer countSuccsesReservation;
    private Float rating;

    public OrganizationStatistics(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Integer getCountSuccsesReservation() {
        return countSuccsesReservation;
    }

    public void setCountSuccsesReservation(Integer countSuccsesReservation) {
        this.countSuccsesReservation = countSuccsesReservation;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
