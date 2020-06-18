package WebApp.entity;

import WebApp.entity.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Service extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "description", length = 1020)
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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private List<Report> reports;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    public Service() {
    }

    public Service(String name, Float price, String description, Integer time, Float rating, Organization organization, List<Reservation> reservations, List<Report> reports, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.time = time;
        this.rating = rating;
        this.organization = organization;
        this.reservations = reservations;
        this.reports = reports;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", rating=" + rating +
                ", organization=" + organization +
                ", reservations=" + reservations +
                ", category=" + category +
                '}';
    }
}
