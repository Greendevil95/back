package WebApp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "organization")
public class Organization extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "finish_time")
    private LocalTime finishTime;

    @Column(name = "weekend")
    private String weekend;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
    private List<Service> services;

    //    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="email")
    //    @JsonIdentityReference(alwaysAsId=true)


    public Organization() {
    }

    public Organization(String name, String address, String phoneNumber, String description, Float rating, LocalTime startTime, LocalTime finishTime, String weekend, User user, List<Service> services) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.rating = rating;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.weekend = weekend;
        this.user = user;
        this.services = services;
    }

    public String getWeekend() {
        return weekend;
    }

    public void setWeekend(String weekend) {
        this.weekend = weekend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
