package WebApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Service extends AbstractEntity{
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private List<Reservation> reservations;

    public Service() {
    }

    public Service(String description, Organization organization, List<Reservation> reservations) {
        this.description = description;
        this.organization = organization;
        this.reservations = reservations;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getDescription() {
        return description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
