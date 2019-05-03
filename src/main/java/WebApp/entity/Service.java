package WebApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Service extends AbstractEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "description")
    private String description;

    @Column(name = "time")
    private Integer time;

    @Column(name = "rating")
    private Float rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private List<Reservation> reservations;

    public Service() {
    }

    public Service(String description, Organization organization, Integer time, List<Reservation> reservations) {
        this.description = description;
        this.organization = organization;
        this.time = time;
        this.reservations = reservations;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Integer getTime() {
        return time;
    }

    public Float getRating() {
        return rating;
    }

    public Organization getOrganization() {
        return organization;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
