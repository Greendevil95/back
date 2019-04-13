package WebApp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organization")
public class Organization extends AbstractEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "description")
    private  String description;

    @Column(name = "rating")
    private Float rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
    private List<Service> services ;

    //    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="email")
    //    @JsonIdentityReference(alwaysAsId=true)


    public Organization() {
    }

    public Organization(String name, String address, String phoneNumber, String description, Float rating, User user, List<Service> services) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.rating = rating;
        this.user = user;
        this.services = services;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public Float getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }

    public List<Service> getServices() {
        return services;
    }
}
