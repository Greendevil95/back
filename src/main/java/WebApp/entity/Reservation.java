package WebApp.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "reservation")
public class Reservation extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(name = "comment")
    private String comment;

    @ElementCollection(targetClass = ReservationStatus.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "reservation_status", joinColumns = @JoinColumn(name = "reservation_id"))
    @Enumerated(EnumType.STRING)
    private Set<ReservationStatus> status;

    @Column(name = "rating")
    private float rating;

    @Column(name = "date")
    private LocalDateTime dateTime;

    @Column(name = "count_reservation")
    private Integer countReservation;

    public Reservation() {
    }

    public Reservation(User user, Service service, String comment, Set<ReservationStatus> status, float rating, LocalDateTime dateTime, Integer countReservation) {
        this.user = user;
        this.service = service;
        this.comment = comment;
        this.status = status;
        this.rating = rating;
        this.dateTime = dateTime;
        this.countReservation = countReservation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<ReservationStatus> getStatus() {
        return status;
    }

    public void setStatus(Set<ReservationStatus> status) {
        this.status = status;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getCountReservation() {
        return countReservation;
    }

    public void setCountReservation(Integer countReservation) {
        this.countReservation = countReservation;
    }
}
