package WebApp.entity;

import javax.persistence.*;

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

    @Column(name = "rating")
    private float rating;

    public Reservation() {
    }

    public Reservation(User user, Service service, String comment, float rating) {
        this.user = user;
        this.service = service;
        this.comment = comment;
        this.rating = rating;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public Service getService() {
        return service;
    }

    public String getComment() {
        return comment;
    }

    public float getRating() {
        return rating;
    }
}
